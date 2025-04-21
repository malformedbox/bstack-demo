param([string]$codebase = "STABLE", [string]$email = "qaa@higherlogic.com", [string]$build = (Get-Date -Format "Mdyyhss"), [string]$domain = "regressiontestrun")

#Check that the codebase is valid.  If not stop build.
function checkCodeBase($codebase)
{
    if ($codebase.ToUpper().Equals("BUSINESSLAYER") -or
            $codebase.ToUpper().Equals("STABLE") -or
            $codebase.ToUpper().Equals("QA1") -or
            $codebase.ToUpper().Equals("QA2") -or
            $codebase.ToUpper().Equals("QA3") -or
            $codebase.ToUpper().Equals("QA4") -or
            $codebase.ToUpper().Equals("QA6") -or
            $codebase.ToUpper().Equals("QA7") -or
            $codebase.ToUpper().Equals("QA8") -or
            $codebase.ToUpper().Equals("QA9") -or
            $codebase.ToUpper().Equals("QA10") -or
            $codebase.ToUpper().Equals("QA5") -or
            $codebase.ToUpper().Contains("ONLINE-COMMUNITY-PR"))
    {

        return $true;
    }
    else
    {
        throw "The codebase is not known."
    }
}
#Adds sql parameters to a query.
function AddSQLParametersForMenu($Database)
{
    $Command.Parameters.ADD((New-Object Data.SqlClient.SqlParameter("@FromTenantCode", [Data.SQLDBType]::NVarChar)));
    $Command.Parameters.ADD((New-Object Data.SqlClient.SqlParameter("@FromMicrositeKey", [Data.SQLDBType]::UniqueIdentifier)));
    $Command.Parameters.Add((New-Object Data.SqlClient.SqlParameter("@ToMicrositeKey", [Data.SQLDBType]::UniqueIdentifier)));
    $Command.Parameters.Add((New-Object Data.SqlClient.SqlParameter("@OnlyCopyToMainSite", [Data.SQLDBType]::Bit)));
    $Command.Parameters.Add((New-Object Data.SqlClient.SqlParameter("@ReplaceExisting", [Data.SQLDBType]::Bit)));
    $Command.Parameters.Add((New-Object Data.SqlClient.SqlParameter("@AvoidPageCodes", [Data.SQLDBType]::NVarChar)));
    $Command.Parameters.Add((New-Object Data.SqlClient.SqlParameter("@DBServer", [Data.SQLDBType]::NVarChar)));

    $Command.Parameters[0].Value = "THRIVEAUTOMATION"; #THRIVEAUTOMATION TenantCode
    $Command.Parameters[1].Value = [GUID]"53944F0A-F944-40D3-A27E-EE7C349BE203"; #THRIVEAUTOMATION site MicrositeKey
    [string]$MicrositesiteKeyQuery = $( "
        SELECT * FROM Microsite
        WHERE SiteName = '" + $SiteName + "'
    " );
    $resultsData1 = New-Object System.Data.DataTable;
    $resultsData1 = ExecuteSqlQuery $Server $Database $MicrositesiteKeyQuery $UserId $Pass;
    $command.Parameters[2].Value = [GUID]$resultsData1.MicrositeKey; #New site MicrositeKey
    [string]$AvoidPageCodes = "member-home-redirect-for-admins";
    $Command.Parameters[5].Value = $AvoidPageCodes;
    $Command.Parameters[6].Value = $Server; #Only want to add the Ad Tests menu and pages
}
function AddSQLParamtersForQueue()
{
    $Command.Parameters.ADD((New-Object Data.SqlClient.SqlParameter("@suffix", [Data.SQLDBType]::NVarChar)));
    if ( $codebase.ToUpper().Equals("QA5"))
    {
        $Command.Parameters[0].Value = "Stable";
    }
    elseif ($codebase.ToUpper().Equals("MASTER"))
    {
        $Command.Parameters[0].Value = "Stable";
    }
    elseif ($codebase.ToUpper().Contains("ONLINE-COMMUNITY-PR"))
    {
        $Command.Parameters[0].Value = "Stable";
    }
    elseif ($codebase.ToUpper().Equals("STABLE"))
    {
        $Command.Parameters[0].Value = "Stable";
    }
    elseif ($codebase.ToUpper().Contains("QA"))
    {
        $Command.Parameters[0].Value = "Stable";
    }
    else
    {
        $Command.Parameters[0].Value = $codebase.Substring(0, 1).ToUpper() + $codebase.Substring(1).ToLower();
    }
}

#Executes a query and populates the $datatable with the data.
function ExecuteSqlQuery($Server, $Database, $SQLQuery, $UserID, $Pass)
{
    $Datatable = New-Object System.Data.DataTable;

    $Connection = New-Object System.Data.SQLClient.SQLConnection;
    $Connection.ConnectionString = "server='$Server';database='$Database';trusted_connection=true; Integrated Security=true;";
    try
    {
        $Connection.Open();
    }
    catch
    {
        Write-Host('A connection was not able to be made.  Exiting script');
        exit;
    }
    $Command = New-Object System.Data.SQLClient.SQLCommand;
    $Command.Connection = $Connection;
    $Command.CommandText = $SQLQuery;
    try
    {
        $Reader = $Command.ExecuteReader();
        $Datatable.Load($Reader);
    }
    catch
    {
        $Connection.Close();
        Write-Host("Query did not run successfully, ending script.");
        exit;
    }
    $Connection.Close();
    Write-Host("Query ran successfully.");
    return $Datatable;
}

#Executes a query with parameters and populates the $datatable with the data.
function ExecuteSqlQueryWithParameters($Server, $Database, $SQLQuery, $UserID, $Pass, $Procedure)
{
    $Datatable = New-Object System.Data.DataTable;

    $Connection = New-Object System.Data.SQLClient.SQLConnection;
    $Connection.ConnectionString = "server='$Server';database='$Database';trusted_connection=true; Integrated Security=true;";
    $Connection.Open();
    $Command = New-Object System.Data.SQLClient.SQLCommand;
    $Command.Connection = $Connection;
    $Command.CommandType = [System.Data.CommandType]::StoredProcedure;
    if ($Procedure.ToUpper() -eq "MENU")
    {
        AddSQLParametersForMenu $Database;
    }
    elseif ($Procedure.ToUpper() -eq "QUEUE")
    {
        AddSQLParamtersForQueue
    }
    else
    {
        Write-Host("Unknown procedure with parameters.");
        exit;
    }
    $Command.CommandText = $SQLQuery;
    try
    {
        $Reader = $Command.ExecuteReader();
        $Datatable.Load($Reader);
    }
    catch [Exception]
    {
        #Write-Host("$_.Exception.GetType().FullName $_.Exception.Message");
        $Connection.Close();
        Write-Host("Query did not run successfully, ending script.");
        exit;
    }
    $Connection.Close()
    Write-Host("Query ran successfully.");
    return $Datatable
}

#First check if the codebase is valid.  If not fail the step which will stop the build.
checkCodeBase $codebase

[string]$TopLevelDomain = ".connectedcommunity.org"
[string]$SiteName = $domain + $build;
[string]$Server = "tenantsqlserver";
[string]$UserID = "";
[string]$Pass = "";
[string]$tk = "";
[string]$WWWSiteURL = $SiteName + $TopLevelDomain;

#Add row to TenantApplication to start the provisioning process.  There are business logic
#rules for the values being entered into the db.  These are inherent as it stands but essentially
#there can be no whitespace (trim), TenantCode cannot be null, there are reserved TenantCodes,
#cannot have duplicate domains, WWWSiteUrl must be all lowercase, email cannot be null.
[string]$Database = "TENANT_ACTUAL"
[string]$AddTenantApplicationQuery = $(
"USE " + $Database + "
     INSERT INTO TenantApplication (TenantKey, TenantCode, TenantShortName, TenantFullName, WWWSiteURL, CountryCode, ProductFlags, CreatedOn, EmailAddress, Finished, IsTestDemoSite, IsSalesDemoSite, ModelSiteName, ModelKey)
     VALUES(NEWID(), '" + $SiteName + "', '" + $SiteName + "', '" + $SiteName + "', '" + $WWWSiteURL + "', 'US', '000000000', GETDATE(), '" + $email + "', '0', '1', '0', 'associationmodel','6AEE003F-CF91-4288-BFBF-A07F60FE69FA');
" );
Write-Host("Adding entry to TenantApplication table.");
ExecuteSqlQuery $Server $Database $AddTenantApplicationQuery $UserId $Pass;

#Get the TenantKey for the new site.
[string]$TenantApplicationTenantKeyQuery = $( "
    USE " + $Database + "
    SELECT TenantKey FROM TenantApplication WHERE TenantCode = '" + $SiteName + "'
" );
Write-Host("Getting the TenantKey for the new site.");
$TenantKeyResults = New-Object System.Data.DataTable;
$TenantKeyResults = ExecuteSqlQuery $Server $Database $TenantApplicationTenantKeyQuery $UserId $Pass;

#Use TenantKey to check if the TenantApplicationSteps for the site have completed.  If the FinishedOn value is null,
#then the step has not completed so the site is not ready.  Pulse to until all steps are complete.
$tk = $TenantKeyResults.TenantKey;
[string]$CreateSiteStatusQuery = $( "
    USE " + $Database + "
    SELECT Count(*) AS 'Finished' FROM TenantApplicationStep WHERE TenantKey = '" + $tk + "' AND FinishedOn IS NOT NULL
" );
[int]$finished = 0;
while ($finished -lt "6")
{
    Write-Host ("Checking TenantApplicationSteps status.");
    Start-Sleep -s 60;
    $SiteStatusResults = New-Object System.Data.DataTable;
    $SiteStatusResults = ExecuteSqlQuery $Server $Database $CreateSiteStatusQuery $UserId $Pass;
    $finished = $SiteStatusResults.Finished;
}
Write-Host ("TenantApplicationSteps completed.");

#Add product license key values for tenant to TenantLicensedProduct table.
[string]$Database = "TENANT_ACTUAL"
[string]$AddProductFlagsQuery = $(
"USE " + $Database + "
    INSERT INTO TenantLicensedProduct (TenantKey, ProductKey)
    VALUES('" + $tk + "', '0826FD0F-62FD-4A5F-84CD-C79FAAF1CCA0'),
    ('" + $tk + "', '16361573-A1C8-4DE4-9B7A-66C8DE38C1FA'),
    ('" + $tk + "', '1FA83955-30EF-40D4-8A9F-FC245CE15789'),
    ('" + $tk + "', '4CC928F2-154D-47FA-A497-D4CDC1534419'),
    ('" + $tk + "', '647E73DB-E98A-4580-87D7-E7CF90ADEA52'),
    ('" + $tk + "', '683FF41A-A43B-4A25-B543-51B8E0965A13'),
    ('" + $tk + "', '6C2172D3-1A82-40A6-942C-F50A5741083A'),
    ('" + $tk + "', '84B16707-55FB-44D6-9E64-72D78D58FFA7'),
    ('" + $tk + "', 'A21318E6-93C4-4D64-AFE4-6F2097BA7D3B'),
    ('" + $tk + "', 'B72ED2E0-55BB-4FE2-B757-565D7AFA98DB'),
    ('" + $tk + "', 'BB8E1C0A-EA13-4A39-9A85-B5791577CD73'),
    ('" + $tk + "', 'BBC81B47-7E8C-4619-B3A3-9B9803BAEF2D'),
    ('" + $tk + "', 'E53E43BB-BFCD-4CCA-A944-36293AF6FE79');
");
Write-Host("Adding product flags.");
ExecuteSqlQuery $Server $Database $AddProductFlagsQuery $UserId $Pass;

#Update the js/css for the site.
[string]$UpdateAllowedRequestSchemas = $( "
    USE " + $Database + "
    UPDATE TenantDomain
    SET AllowedRequestSchemas = '2'
    WHERE SiteDomain like '%" + $SiteName + "%'
" );
Write-Host("Updating JS/CSS for the site.");
ExecuteSqlQuery $Server $Database $UpdateAllowedRequestSchemas $UserId $Pass;

#Update the codebase
[string]$CodeBaseKey = "9AAD2E5F-A00A-45E3-86CB-28A19D123C53"; #Default to stable.
[string]$UpdateCodebaseQuery = $( "
    UPDATE " + $Database + "..Tenant
    SET CodeBaseKey = '" + $CodeBaseKey + "'
    WHERE TenantKey =  '" + $tk + "'
" )
Write-Host("Updating site codebase.");
ExecuteSqlQuery $Server $Database $UpdateCodebaseQuery $UserId $Pass;

#Set the AIFeatureKey GUID for Library Bulk Upload with AI. The BulkUploadEnabled parameter is no longer used.
# Generate a random GUID
$guid = [guid]::NewGuid()
[string]$InsertBulkUploadEnabled = $( "
    INSERT INTO TenantAIFeature (TenantAIFeatureKey, TenantKey, AIFeatureKey)
    VALUES ('$guid', '$tk','DCFBA222-0C0C-4ED0-95FF-6E9A661C05A4')
" )
Write-Host("Enabling Bulk Upload.");
$SetBulkUploadEnabledResults = New-Object System.Data.DataTable;
$SetBulkUploadEnabledResults = ExecuteSqlQuery $Server $Database $InsertBulkUploadEnabled $UserId $Pass;

#Set the AIFeatureKey GUID for AI Formal Tag Suggestions. The EnablePrimary and EnableSuggestedTags parameter are no longer used.
# Generate a random GUID
$guid = [guid]::NewGuid()
[string]$InsertTagSuggestionsEnabled = $( "
    INSERT INTO TenantAIFeature (TenantAIFeatureKey, TenantKey, AIFeatureKey)
    VALUES ('$guid', '$tk','42BD4C36-DF63-48B9-A92D-CAAEE473EED3')
" )
Write-Host("Enabling AI Formal Tag Suggestions.");
$SetTagSuggestionsEnabledResults = New-Object System.Data.DataTable;
$SetTagSuggestionsEnabledResults = ExecuteSqlQuery $Server $Database $InsertTagSuggestionsEnabled $UserId $Pass;

$Server = "sqldev03";
#Use new tenant's db from this point
$Database = $SiteName;

#Update the menu type enum
[string]$UpdateMenuTypeEnum = $( "
    UPDATE " + $SiteName + "..Microsite
    SET MenuTypeEnum = '9'
    WHERE SiteTitle = '" + $SiteName + "'
" )
Write-Host("Updating menu type.");
ExecuteSqlQuery $Server $Database $UpdateMenuTypeEnum $UserId $Pass;

#Update the isAssociationModelMicrosite
[string]$UpdateIsAssociationModelMicrosite = $( "
    UPDATE " + $SiteName + "..Microsite
    SET isAssociationModelMicrosite = '1'
    WHERE SiteTitle = '" + $SiteName + "'
" )
Write-Host("Setting isAssociationModelMicrosite to one.");
ExecuteSqlQuery $Server $Database $UpdateIsAssociationModelMicrosite $UserId $Pass;

#Copy atMember SecurityGroupRef.
[string]$InsertMemberSecurityGroup = $( "
    INSERT INTO " + $Database + "..SecurityGroupRef
    SELECT * FROM THRIVEAUTOMATION..SecurityGroupRef
    WHERE SecurityGroupKey = 'CBAC2F7E-AB7F-4ED3-A28A-01835B30466A'
" );
Write-Host("Copying security group reference for members.");
$SecurityGroupResults = New-Object System.Data.DataTable;
$SecurityGroupResults = ExecuteSqlQuery $Server $Database $InsertMemberSecurityGroup $UserId $Pass;

#Copy the AddressKey from Thriveautomation DB to the new site DB.
[string]$InsertUserAddressQuery = $(
"INSERT INTO [" + $Database + "]..Address
     SELECT a.* FROM THRIVEAUTOMATION..Address a inner join THRIVEAUTOMATION..Contact c ON a.AddressKey = c.AddressKey
     WHERE
     LastName in ('Test','Diego','Admin','opt-out','Author')  AND
         FirstName in (
     	    'Member', 'volunteerfan', 'communityadmin', 'Grandpa', 'nonmember',
     	    'Authenticatednonmember', 'gmailuser1', 'superadmin', 'VolunteerFan2',
     	    'volunteeradmin', 'restricteduser', 'noncommunitymember', 'communitymember',
     	    'authenticated', 'grandma', 'moderateduser', 'InternSean', 'Developer', 'Tester',
     	     'Super', 'Directory', 'Blog', 'Baby', 'Siteadmin',
     	     'Daenerys', 'Authenticatednonmember2', 'communitymoderator',
     	     'Pronouns', 'Mentor'
         ) OR FirstName = 'Member'"
);
Write-Host("Copying address info from Thriveautomation database to new site database.");
$AddressResults = New-Object System.Data.DataTable;
$AddressResults = ExecuteSqlQuery $Server $Database $InsertUserAddressQuery $UserId $Pass;

#Copy the AddressKey of Companies from Thriveautomation DB to the new site DB.
[string]$InsertCompaniesAddressQuery = $(
"INSERT INTO [" + $Database + "]..Address
     SELECT a.* FROM THRIVEAUTOMATION..Address a inner join THRIVEAUTOMATION..Contact c ON a.AddressKey = c.AddressKey
     WHERE CompanyName in ('Better Business Bureau','Small Business Majority');"
);
Write-Host("Copying address of Companies info from Thriveautomation database to new site database.");
$CompaniesAddressResults = New-Object System.Data.DataTable;
$CompaniesAddressResults = ExecuteSqlQuery $Server $Database $InsertCompaniesAddressQuery $UserId $Pass;


#Copy the contact information from the Thriveautomation DB to the new site DB.
[string]$InsertUserContactQuery = $( "
    INSERT INTO " + $Database + "..Contact
    SELECT * FROM THRIVEAUTOMATION..Contact
    WHERE
    LastName in ('Test','Diego','Admin','opt-out','Author')  AND
        FirstName in (
    	    'Member', 'volunteerfan', 'communityadmin', 'Grandpa', 'nonmember',
    	    'Authenticatednonmember', 'gmailuser1', 'superadmin', 'VolunteerFan2',
    	    'volunteeradmin', 'restricteduser', 'noncommunitymember', 'communitymember',
    	    'authenticated', 'grandma', 'moderateduser', 'InternSean', 'Developer', 'Tester',
    	     'Super', 'Directory', 'Blog', 'Baby', 'Siteadmin',
    	     'Daenerys', 'Authenticatednonmember2', 'communitymoderator',
    	     'Pronouns', 'Mentor'
        ) OR FirstName = 'Member'"
);
Write-Host("Copying contacts from Thriveautomation database to new site database.");
$ContactResults = New-Object System.Data.DataTable;
$ContactResults = ExecuteSqlQuery $Server $Database $InsertUserContactQuery $UserId $Pass;

#Copy the companies information needed from the Thriveautomation DB to the new site DB.
[string]$InsertCompaniesContactQuery = $( "
    INSERT INTO " + $Database + "..Contact
    SELECT * FROM THRIVEAUTOMATION..Contact
    WHERE
    EmailAddress in ('bbb@higherlogic.test','sbm@higherlogic.test')"
);
Write-Host("Copying companies from Thriveautomation database to new site database.");
$CompaniesResults = New-Object System.Data.DataTable;
$CompaniesResults = ExecuteSqlQuery $Server $Database $InsertCompaniesContactQuery $UserId $Pass;

#Copy ContactSecurityGroup.
[string]$InsertContactSecurityGroup = $( "
    INSERT INTO ContactSecurityGroup
    SELECT * FROM THRIVEAUTOMATION..ContactSecurityGroup
    WHERE SecurityGroupKey = 'CBAC2F7E-AB7F-4ED3-A28A-01835B30466A'
" );
Write-Host("Copying ContactSecurityGroup.");
$ContactSecurityGroupResults = New-Object System.Data.DataTable;
$ContactSecurityGroupResults = ExecuteSqlQuery $Server $Database $InsertContactSecurityGroup $UserId $Pass;

#Skipped all stuff for  moderated user
#Skipped Set the privacy default settings to all on and all members only - already set to members by default

# Disable Moxie Manager for Blogs
[string]$InsertDisableMoxieForBlogs = $( "
    INSERT INTO " + $Database + "..[Configuration]
    SELECT * FROM THRIVEAUTOMATION..[Configuration]
    WHERE ParameterName = 'DisableMoxieManagerForBlogs'
" );
Write-Host("Disabling Moxie Manager for Blogs.");
$MoxieManagerResults = New-Object System.Data.DataTable;
$MoxieManagerResults = ExecuteSqlQuery $Server $Database $InsertDisableMoxieForBlogs $UserId $Pass;

#Set the isAutomation flag to True.  This turns off the development border as it causes issues when the tests run.
[string]$InsertIsAutomationQuery = $( "
    INSERT INTO " + $Database + "..[Configuration]
    SELECT * FROM THRIVEAUTOMATION..[Configuration]
    WHERE ParameterName = 'IsAutomation'
" )
Write-Host("Turning on isAutomation.");
$IsAutomationResults = New-Object System.Data.DataTable;
$IsAutomationResults = ExecuteSqlQuery $Server $Database $InsertIsAutomationQuery $UserId $Pass;

#Set the ModerateFrequentContributions flag to False.  This removes the posting restrictions for a user.
[string]$UpdateModerateFrequentContributionsQuery = $( "
    INSERT INTO " + $Database + "..[Configuration]
    SELECT * FROM THRIVEAUTOMATION..[Configuration]
    WHERE ParameterName = 'ModerateFrequentContributions'
" )
Write-Host("Turning off moderation restrictions.");
$ModerationRestrictionsResults = New-Object System.Data.DataTable;
$ModerationRestrictionsResults = ExecuteSqlQuery $Server $Database $UpdateModerateFrequentContributionsQuery $UserId $Pass;

#Set the BypassScheduledDeletion flag to True.  This allows for access to an OC tenant that is scheduled for deletion.
[string]$UpdateBypassScheduledDeletionQuery = $( "
    INSERT INTO " + $Database + "..[Configuration]
    SELECT * FROM THRIVEAUTOMATION..[Configuration]
    WHERE ParameterName = 'BypassScheduledDeletion'
" )
Write-Host("Enabling Deletion Bypassing");
$BypassDeletionResults = New-Object System.Data.DataTable;
$BypassDeletionResults = ExecuteSqlQuery $Server $Database $UpdateBypassScheduledDeletionQuery $UserId $Pass;


#Skipped for now. Set the IsRedirectUrlValidated flag to False.  This turns off the URL redirect validation that will break the ads tests.
#DistributeCacheInvalidation flag already is set to True.

#Set the volunteer points per hour.
[string]$SetVolunteerPointsPerHour = $( "
    INSERT INTO " + $Database + "..[Configuration]
    SELECT * FROM THRIVEAUTOMATION..[Configuration]
    WHERE ParameterName =  'VolunteerPointsPerHour'
" )
Write-Host("Updating volunteer points per hour.");
$SetVolunteerPointsPerHourResults = New-Object System.Data.DataTable;
$SetVolunteerPointsPerHourResults = ExecuteSqlQuery $Server $Database $SetVolunteerPointsPerHour $UserId $Pass;

#Update LoginSessionLengthMinutes to 720 for 12 hour sessions. This value is copied from Thriveautomation Tenant.
[string]$UpdateLoginSessionLengthMinutesQuery = $( "
    INSERT INTO " + $Database + "..[Configuration]
    SELECT * FROM THRIVEAUTOMATION..[Configuration]
    WHERE ParameterName = 'LoginSessionLengthMinutes'
" )
Write-Host("Updating LoginSessionLengthMinutes to 720 minutes.");
$UpdateLoginSessionLengthMinutesResults = New-Object System.Data.DataTable;
$UpdateLoginSessionLengthMinutesResults = ExecuteSqlQuery $Server $Database $UpdateLoginSessionLengthMinutesQuery $UserId $Pass;

#Set ShowShareWidgetOnEventDetails to True.
[string]$UpdateShowShareWidgetOnEventDetailsQuery = $( "
    USE " + $Database + "
    UPDATE Configuration
    SET ParameterValue = 'True'
    WHERE ParameterName = 'ShowShareWidgetOnEventDetails'
" )
Write-Host("Enabling social sharing for Events.");
$UpdateShowShareWidgetOnEventResults = New-Object System.Data.DataTable;
$UpdateShowShareWidgetOnEventResults = ExecuteSqlQuery $Server $Database $UpdateShowShareWidgetOnEventDetailsQuery $UserId $Pass;

#Set ShowShareWidgetOnOpportunityDetails to True.
[string]$UpdateShowShareWidgetOnOpportunityDetailsQuery = $( "
    USE " + $Database + "
    UPDATE Configuration
    SET ParameterValue = 'True'
    WHERE ParameterName = 'ShowShareWidgetOnOpportunityDetails'
" )
Write-Host("Enabling social sharing for Volunteer Opportunities.");
$UpdateShowShareWidgetOnOpportunityResults = New-Object System.Data.DataTable;
$UpdateShowShareWidgetOnOpportunityResults = ExecuteSqlQuery $Server $Database $UpdateShowShareWidgetOnOpportunityDetailsQuery $UserId $Pass;

#Turn off the requires acceptance setting for terms and conditions.
[string]$UpdateTermsandConditionsQuery = $( "
    UPDATE " + $Database + "..TermsAndConditions
    SET RequireAcceptance = '0'
    WHERE TermsAndConditionsKey =  '02786337-EAAB-4BB8-A426-E444A7494098'
" )
Write-Host("Turning off terms and conditions from be required.");
$TermsAndConditionsResults = New-Object System.Data.DataTable;
$TermsAndConditionsResults = ExecuteSqlQuery $Server $Database $UpdateTermsandConditionsQuery $UserId $Pass;

#Turn off recaptcha.
[string]$UpdateDisableRecaptcha = $( "
    UPDATE " + $Database + "..Configuration
    SET ParameterValue = 'True'
    WHERE ParameterName = 'DisableRecaptcha'
" )
Write-Host("Turning off recaptcha.");
$DisableRecaptchaResults = New-Object System.Data.DataTable;
$DisableRecaptchaResults = ExecuteSqlQuery $Server $Database $UpdateDisableRecaptcha $UserId $Pass;

#Just want to see what happens without it. Set the release channel key for the tenant.

#Copy the Ad Test menu and pages from THRIVEAUTOMATION DB to the new site DB.
[string]$RunCopyAllNavigationFromTenantStoredProcedure = "CopyAllNavigationFromTenant";
Write-Host("Copying menus from THRIVEAUTOMATION menu to new site menu.");
$CopyNavigationResults = New-Object System.Data.DataTable;
$CopyNavigationResults = ExecuteSqlQueryWithParameters $Server $Database $RunCopyAllNavigationFromTenantStoredProcedure $UserId $Pass "menu";

#Update queue route suffix table accordingly with sproc.
#[string] $UpdateQueueRouteSuffixForTenantStoredProcedure = "sp_RouteQueues";
#Write-Host("Updating queue route suffix.");
#$UpdateQueueRouteResults = New-Object System.Data.DataTable;
#$UpdateQueueRouteResults = ExecuteSqlQueryWithParameters $Server $Database $UpdateQueueRouteSuffixForTenantStoredProcedure $UserId $Pass "queue";


# Create a new row in the IAM table for tenant being provisioned by copying an existing row in the DB and replacing with a new random IamKey and TenantKey
$Server= $sqldevha01;
$Database = "TENANT_ACTUAL"
[string] $IamKey = New-Guid;
[string] $CommunityMemberKey = New-Guid;
[string] $CommunityAdminKey = New-Guid;
[string] $CopyIAMKeyQuery = $("
  INSERT INTO IAM (IAMKey, TenantKey, ContactKey, Description, ActiveFlag, CreatedOn, Password, CreatedByContactKey)
  SELECT '" + $IamKey + "', '" + $tk + "', ContactKey, Description, ActiveFlag, GETDATE(), Password, '7F2DE571-92E8-49B0-BA12-27413BF99C95'
  FROM IAM
  WHERE TenantKey = '248787EC-83DF-41DB-A0F0-6673F1CD029B' AND ContactKey = '7bbb2b9d-1e5c-49ef-8043-0183477c3c8f'
");
Write-Host("Creating new super admin row in IAM table");
$CopyIAMKeyQueryResults = New-Object System.Data.DataTable;
$CopyIAMKeyQueryResults = ExecuteSqlQuery $Server $Database $CopyIAMKeyQuery $UserId $Pass;

[string] $CopyCommunityMemberKeyQuery = $("
  INSERT INTO IAM (IAMKey, TenantKey, ContactKey, Description, ActiveFlag, CreatedOn, Password, CreatedByContactKey)
  SELECT '" + $CommunityMemberKey + "', '" + $tk + "', ContactKey, Description, ActiveFlag, GETDATE(), Password, '7F2DE571-92E8-49B0-BA12-27413BF99C95'
  FROM IAM
  WHERE TenantKey = '248787EC-83DF-41DB-A0F0-6673F1CD029B' AND ContactKey = 'a78eab47-dbff-4641-b470-0183477907ab'
");
Write-Host("Creating new community member row in IAM table");
$CopyCommunityMemberKeyQueryResults = New-Object System.Data.DataTable;
$CopyCommunityMemberKeyQueryResults = ExecuteSqlQuery $Server $Database $CopyCommunityMemberKeyQuery $UserId $Pass;

[string] $CopyCommunityAdminKeyQuery = $("
  INSERT INTO IAM (IAMKey, TenantKey, ContactKey, Description, ActiveFlag, CreatedOn, Password, CreatedByContactKey)
  SELECT '" + $CommunityAdminKey + "', '" + $tk + "', ContactKey, Description, ActiveFlag, GETDATE(), Password, '7F2DE571-92E8-49B0-BA12-27413BF99C95'
  FROM IAM
  WHERE TenantKey = '248787EC-83DF-41DB-A0F0-6673F1CD029B' AND ContactKey = '45962bfc-53aa-4241-8c1e-018347789c8e'
");
Write-Host("Creating new community admin row in IAM table");
$CopyCommunityAdminKeyQueryResults = New-Object System.Data.DataTable;
$CopyCommunityAdminKeyQueryResults = ExecuteSqlQuery $Server $Database $CopyCommunityAdminKeyQuery $UserId $Pass;

# Query to retrieve the IAMKey and Description
[string] $RetrieveIAMKeyQuery = $("
  SELECT IAMKey, Description
  FROM IAM
  WHERE IAMKey = '" + $IamKey + "'
");

$RetrieveIAMKeyQueryResults = New-Object System.Data.DataTable;
$RetrieveIAMKeyQueryResults = ExecuteSqlQuery $Server $Database $RetrieveIAMKeyQuery $UserId $Pass;

# Extract IAMKey and Description
[string] $RetrievedIAMKey = $RetrieveIAMKeyQueryResults.IAMKey;
[string] $Description = $RetrieveIAMKeyQueryResults.Description;

# Query to retrieve the IAMKey and Description
[string] $RetrieveCommunityMemberIAMKeyQuery = $("
  SELECT IAMKey, Description
  FROM IAM
  WHERE IAMKey = '" + $CommunityMemberKey + "'
");

$RetrieveCommunityMemberIAMKeyResults = New-Object System.Data.DataTable;
$RetrieveCommunityMemberIAMKeyResults = ExecuteSqlQuery $Server $Database $RetrieveCommunityMemberIAMKeyQuery $UserId $Pass;

# Extract IAMKey and Description
[string] $RetrievedCommunityMemberKey = $RetrieveCommunityMemberIAMKeyResults.IAMKey;
[string] $DescriptionCommunityMember = $RetrieveCommunityMemberIAMKeyResults.Description;

# Query to retrieve the IAMKey and Description
[string] $RetrieveCommunityAdminKeyQuery = $("
  SELECT IAMKey, Description
  FROM IAM
  WHERE IAMKey = '" + $CommunityAdminKey + "'
");

$RetrieveCommunityAdminKeyQueryResults = New-Object System.Data.DataTable;
$RetrieveCommunityAdminKeyQueryResults = ExecuteSqlQuery $Server $Database $RetrieveCommunityAdminKeyQuery $UserId $Pass;

# Extract IAMKey and Description
[string] $RetrievedCommunityAdminKey = $RetrieveCommunityAdminKeyQueryResults.IAMKey;
[string] $DescriptionCommunityAdmin = $RetrieveCommunityAdminKeyQueryResults.Description;

# Define the path for your properties file relative to the repository root
$propertiesFilePath = "src/test/resources/test_config.properties"

# Create or overwrite the properties file with the new values
@"
superadminKey=$RetrievedIAMKey
superadminPassword=$Description
communitymemberKey=$RetrievedCommunityMemberKey
communitymemberPassword=$DescriptionCommunityMember
communityadminKey=$RetrievedCommunityAdminKey
communityadminPassword=$DescriptionCommunityAdmin
"@ | Out-File -FilePath $propertiesFilePath -Encoding utf8

Write-Host "Properties file created at: $propertiesFilePath"

Write-Host("Script run to completion.");