param([string]$addressSQLDEVHA01 = "", [string]$addressSQLDEV03 = "", [string]$TENANT_SQL_SERVER = "", [string]$SQL_DB = "", [string]$UserID = "", [string]$Pass = "", [string]$codebase = "STABLE", [string]$email = "qaa@higherlogic.com", [string]$build = (Get-Date -Format "Mdyyhss"), [string]$domain = "regressiontestrun", [string]$tenantType = "newTenant")

[string] $copyFromTenantCode;
[GUID] $copyFromMicrositeKey = [System.Guid]::Empty;
[string] $avoidPageCodes = "";
[GUID] $modelKey = [System.Guid]::Empty;
[GUID[]] $productKeys;
[string] $copyContactWhereClause;

function setupTenantTypeVariables($tenantType) {
    switch ($tenantType) {
        'newTenant' {
            $script:copyFromTenantCode = "AUTOMATION";
            $script:copyFromMicrositeKey = [GUID]"CE66FEE7-22D3-4470-9F67-31929E2B5EA6";
            $script:modelKey = "2C768D8D-80F5-4B0B-9163-14DC9421EBCB";
            $script:productKeys = @(
                "0826FD0F-62FD-4A5F-84CD-C79FAAF1CCA0", "16361573-A1C8-4DE4-9B7A-66C8DE38C1FA", "1FA83955-30EF-40D4-8A9F-FC245CE15789",
                "4CC928F2-154D-47FA-A497-D4CDC1534419", "647E73DB-E98A-4580-87D7-E7CF90ADEA52", "683FF41A-A43B-4A25-B543-51B8E0965A13",
                "6C2172D3-1A82-40A6-942C-F50A5741083A", "84B16707-55FB-44D6-9E64-72D78D58FFA7", "A21318E6-93C4-4D64-AFE4-6F2097BA7D3B",
                "B72ED2E0-55BB-4FE2-B757-565D7AFA98DB", "BB8E1C0A-EA13-4A39-9A85-B5791577CD73", "BBC81B47-7E8C-4619-B3A3-9B9803BAEF2D",
                "E53E43BB-BFCD-4CCA-A944-36293AF6FE79"
            )
            $script:copyContactWhereClause = "LastName in ('Test','Diego','Admin','opt-out','Author','Bureau', 'Majority')  AND
                FirstName in (
                    'Member', 'volunteerfan', 'communityadmin', 'Grandpa', 'nonmember',
                    'Authenticatednonmember', 'gmailuser1', 'superadmin', 'VolunteerFan2',
                    'volunteeradmin', 'restricteduser', 'noncommunitymember', 'communitymember',
                    'authenticated', 'grandma', 'moderateduser', 'InternSean', 'Developer', 'Tester',
                    'Super', 'Directory', 'Blog', 'Better Business', 'Small Business', 'Baby', 'Siteadmin',
                    'Daenerys', 'Authenticatednonmember2', 'Groupmemberone', 'communitymoderator',
                    'Pronouns', 'Mentor', 'Mentee'
                ) OR FirstName = 'Member'";
        }
        'newThriveTenant' {
            $script:copyFromTenantCode = "THRIVEAUTOMATION";
            $script:copyFromMicrositeKey = [GUID]"53944F0A-F944-40D3-A27E-EE7C349BE203";
            $script:avoidPageCodes = "member-home-redirect-for-admins";
            $script:modelKey = "6AEE003F-CF91-4288-BFBF-A07F60FE69FA";
            $script:productKeys = @(
                "0826FD0F-62FD-4A5F-84CD-C79FAAF1CCA0", "16361573-A1C8-4DE4-9B7A-66C8DE38C1FA", "1FA83955-30EF-40D4-8A9F-FC245CE15789",
                "4CC928F2-154D-47FA-A497-D4CDC1534419", "647E73DB-E98A-4580-87D7-E7CF90ADEA52", "683FF41A-A43B-4A25-B543-51B8E0965A13",
                "6C2172D3-1A82-40A6-942C-F50A5741083A", "84B16707-55FB-44D6-9E64-72D78D58FFA7", "A21318E6-93C4-4D64-AFE4-6F2097BA7D3B",
                "B72ED2E0-55BB-4FE2-B757-565D7AFA98DB", "BB8E1C0A-EA13-4A39-9A85-B5791577CD73", "BBC81B47-7E8C-4619-B3A3-9B9803BAEF2D",
                "E53E43BB-BFCD-4CCA-A944-36293AF6FE79"
            );
            $script:copyContactWhereClause = "LastName in ('Test','Diego','Admin','opt-out','Author')  AND
                FirstName in (
                    'Member', 'volunteerfan', 'communityadmin', 'Grandpa', 'nonmember',
                    'Authenticatednonmember', 'gmailuser1', 'superadmin', 'VolunteerFan2',
                    'volunteeradmin', 'restricteduser', 'noncommunitymember', 'communitymember',
                    'authenticated', 'grandma', 'moderateduser', 'InternSean', 'Developer', 'Tester',
                    'Super', 'Directory', 'Blog', 'Baby', 'Siteadmin',
                    'Daenerys', 'Authenticatednonmember2', 'communitymoderator',
                    'Pronouns', 'Mentor', 'Mentee'
                ) OR FirstName = 'Member'";
        }
        'newMembershipTenant' {
            $script:copyFromTenantCode = "THRIVEAUTOMATION";
            $script:copyFromMicrositeKey = [GUID]"53944F0A-F944-40D3-A27E-EE7C349BE203";
            $script:avoidPageCodes = "member-home-redirect-for-admins";
            $script:modelKey = "6AEE003F-CF91-4288-BFBF-A07F60FE69FA";
            $script:productKeys = @(
                "0826FD0F-62FD-4A5F-84CD-C79FAAF1CCA0", "16361573-A1C8-4DE4-9B7A-66C8DE38C1FA", "1FA83955-30EF-40D4-8A9F-FC245CE15789",
                "4CC928F2-154D-47FA-A497-D4CDC1534419", "647E73DB-E98A-4580-87D7-E7CF90ADEA52", "683FF41A-A43B-4A25-B543-51B8E0965A13",
                "84B16707-55FB-44D6-9E64-72D78D58FFA7", "A21318E6-93C4-4D64-AFE4-6F2097BA7D3B", "B72ED2E0-55BB-4FE2-B757-565D7AFA98DB",
                "BBC81B47-7E8C-4619-B3A3-9B9803BAEF2D", "E53E43BB-BFCD-4CCA-A944-36293AF6FE79", "0633ECE3-D78C-45C9-A8C7-338D3E830E8A"
            )
            $script:copyContactWhereClause = "LastName in ('Test','Diego','Admin','opt-out','Author')  AND
                FirstName in (
                    'Member', 'volunteerfan', 'communityadmin', 'Grandpa', 'nonmember',
                    'Authenticatednonmember', 'gmailuser1', 'superadmin', 'VolunteerFan2',
                    'volunteeradmin', 'restricteduser', 'noncommunitymember', 'communitymember',
                    'authenticated', 'grandma', 'moderateduser', 'InternSean', 'Developer', 'Tester',
                    'Super', 'Directory', 'Blog', 'Csvgroupupload', 'Baby', 'Siteadmin',
                    'Daenerys', 'Mentor', 'Mentee', 'Authenticatednonmember2', 'Groupmemberone', 'communitymoderator', 'irenewsamenoar',
                    'irenewdiffnoar', 'irenewsamearunenrolled', 'irenewdiffarunenrolled', 'grenewsamenoar','grenewdiffnoar',
                    'grenewsamearunenrolled', 'grenewdiffarunenrolled', 'stopautorenewaluser', 'iarndscs', 'iarrwsscs',
                    'garndscs', 'garrwsscs', 'inextdayexpire', 'gnextdayexpire', 'iarndscf', 'garndscf', 'iarsuccess', 'garsuccess',
                    'gpnactr','gpnactl','gpnacant','ipnactr','ipnactl','ipnacant', 'iachsuccess', 'iachfailure', 'iachsuccessar',
                    'iachfailurear', 'gachsuccess', 'gachfailure', 'gachsuccessar', 'gachfailurear', 'ipnactrachfail', 'ipnactachfail',
                    'ipnacantachfail', 'gpnactrachfail', 'gpnactachfail', 'gpnacantachfail', 'sirsach', 'dirsach', 'sgrsach', 'dgrsach',
                    'sirfach', 'dirfach', 'sgrfach', 'dgrfach', 'srssiach', 'srsdiach', 'gsrssach', 'gsrsdach', 'ifsd', 'ifsdc', 'Expired'
                ) OR FirstName = 'Member'";
        }
        Default { throw "Unknown tenant type." }
    }
}

#Check that the codebase is valid.  If not stop build.
function checkCodeBase($codebase) {
    Write-Host("Checking codebase $codebase.");
    if ( $codebase.ToUpper().Equals("BUSINESSLAYER") -or
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
            $codebase.ToUpper().Contains("ONLINE-COMMUNITY-PR")) {

        return $true;
    }
    else {
        throw "The codebase is not known."
    }
}
#Adds sql parameters to a query.
function AddSQLParametersForMenu($Database) {
    $Command.Parameters.ADD((New-Object Data.SqlClient.SqlParameter("@FromTenantCode", [Data.SQLDBType]::NVarChar)));
    $Command.Parameters.ADD((New-Object Data.SqlClient.SqlParameter("@FromMicrositeKey", [Data.SQLDBType]::UniqueIdentifier)));
    $Command.Parameters.Add((New-Object Data.SqlClient.SqlParameter("@ToMicrositeKey", [Data.SQLDBType]::UniqueIdentifier)));
    $Command.Parameters.Add((New-Object Data.SqlClient.SqlParameter("@OnlyCopyToMainSite", [Data.SQLDBType]::Bit)));
    $Command.Parameters.Add((New-Object Data.SqlClient.SqlParameter("@ReplaceExisting", [Data.SQLDBType]::Bit)));
    $Command.Parameters.Add((New-Object Data.SqlClient.SqlParameter("@AvoidPageCodes", [Data.SQLDBType]::NVarChar)));
    $Command.Parameters.Add((New-Object Data.SqlClient.SqlParameter("@DBServer", [Data.SQLDBType]::NVarChar)));

    $Command.Parameters[0].Value = $copyFromTenantCode; #Automation TenantCode
    $Command.Parameters[1].Value = $copyFromMicrositeKey; #Automation site MicrositeKey
    [string] $MicrositesiteKeyQuery = $("
        SELECT * FROM Microsite
        WHERE SiteName = '" + $SiteName + "'
    ");
    $resultsData1 = New-Object System.Data.DataTable;
    $resultsData1 = ExecuteSqlQuery $Server $Database $MicrositesiteKeyQuery $UserId $Pass;
    $command.Parameters[2].Value = [GUID]$resultsData1.MicrositeKey; #New site MicrositeKey
    $Command.Parameters[5].Value = $avoidPageCodes;
    $Command.Parameters[6].Value = "sqldev03"; #Only want to add the Ad Tests menu and pages
}
function AddSQLParamtersForQueue() {
    $Command.Parameters.ADD((New-Object Data.SqlClient.SqlParameter("@suffix", [Data.SQLDBType]::NVarChar)));
    if ($codebase.ToUpper().Equals("QA5")) {
        $Command.Parameters[0].Value = "Stable";
    }
    elseif ($codebase.ToUpper().Equals("MASTER")) {
        $Command.Parameters[0].Value = "Stable";
    }
    elseif ($codebase.ToUpper().Contains("ONLINE-COMMUNITY-PR")) {
        $Command.Parameters[0].Value = "Stable";
    }
    elseif ($codebase.ToUpper().Equals("STABLE")) {
        $Command.Parameters[0].Value = "Stable";
    }
    elseif ($codebase.ToUpper().Contains("QA")) {
        $Command.Parameters[0].Value = "Stable";
    }
    else {
        $Command.Parameters[0].Value = $codebase.Substring(0, 1).ToUpper() + $codebase.Substring(1).ToLower();
    }
}

#Grabs the AWS credentials to be used in connection strings.
function grabAWSCredentials () {
    param(
        [string]$TENANT_SQL_SERVER,
        [string]$SQL_DB,
        [string]$addressSQLDEVHA01,
        [string]$addressSQLDEV03
    )

    #Define parameters as JSON strings
    $jsonParams1 = '{"portNumber":["1433"], "localPortNumber":["22011"]}'
    $jsonParams2 = '{"portNumber":["1433"], "localPortNumber":["22012"]}'

    #Start AWS SSM sessions
    #Open a port forward on sqldevha01 (where TENANT_ACTUAL is located)
    Start-Job -ScriptBlock {
        aws ssm start-session --target $using:TENANT_SQL_SERVER --document-name AWS-StartPortForwardingSession --parameters $using:jsonParams1
    }

    Start-Job -ScriptBlock {
        aws ssm start-session --target $using:SQL_DB --document-name AWS-StartPortForwardingSession --parameters $using:jsonParams2
    }

    #Install and import AWS Secrets Manager module
    Install-Module -Name AWS.Tools.SecretsManager -Force -Scope CurrentUser
    Import-Module AWS.Tools.SecretsManager

    #Retrieve secrets
    $secret_id = "sqlserverautomation"
    $secret = Get-SECSecretValue -SecretId $secret_id
    $secret_value = ($secret.SecretString | ConvertFrom-Json)
    $global:UserID = $secret_value.username
    $global:Pass = $secret_value.password
}

#Executes a query and populates the $datatable with the data.
function ExecuteSqlQuery ($Server, $Database, $SQLQuery, $UserID, $Pass) {
    $Datatable = New-Object System.Data.DataTable;

    $Connection = New-Object System.Data.SQLClient.SQLConnection;
    $Connection.ConnectionString = "Server=$Server;Database=$Database;User ID=$UserID;Password=$Pass;";

    try {
        $Connection.Open();
    }
    catch {
        Write-Host('A connection was not able to be made.  Exiting script');
        throw $_.Exception;
    }
    $Command = New-Object System.Data.SQLClient.SQLCommand;
    $Command.Connection = $Connection;
    $Command.CommandText = $SQLQuery;
    try {
        $Reader = $Command.ExecuteReader();
        $Datatable.Load($Reader);
    }
    catch {
        $Connection.Close();
        Write-Host("Query did not run successfully, ending script.");
        throw $_.Exception;
    }
    $Connection.Close();
    Write-Host("Query ran successfully.");
    return $Datatable;
}

#Executes a query with parameters and populates the $datatable with the data.
function ExecuteSqlQueryWithParameters ($Server, $Database, $SQLQuery, $UserID, $Pass, $Procedure) {
    $Datatable = New-Object System.Data.DataTable;

    $Connection = New-Object System.Data.SQLClient.SQLConnection;
    $Connection.ConnectionString = "Server=$Server;Database=$Database;User ID=$UserID;Password=$Pass;";

    $Connection.Open();
    $Command = New-Object System.Data.SQLClient.SQLCommand;
    $Command.Connection = $Connection;
    $Command.CommandType = [System.Data.CommandType]::StoredProcedure;
    if ($Procedure.ToUpper() -eq "MENU") {
        AddSQLParametersForMenu $Database;
    }
    elseif ($Procedure.ToUpper() -eq "QUEUE") {
        AddSQLParamtersForQueue
    }
    else {
        throw New-Object System.ArgumentException "Unknown procedure with parameters.";
    }
    $Command.CommandText = $SQLQuery;
    try {
        $Reader = $Command.ExecuteReader();
        $Datatable.Load($Reader);
    }
    catch [Exception] {
        #Write-Host("$_.Exception.GetType().FullName $_.Exception.Message");
        $Connection.Close();
        Write-Host("Query did not run successfully, ending script.");
        throw $_.Exception;
    }
    $Connection.Close()
    Write-Host("Query ran successfully.");
    return $Datatable
}

function PrepProvisionCLITool () {
    Install-Module -Name AWS.Tools.S3 -Force -Scope CurrentUser
    Import-Module AWS.Tools.S3

    $bucket = "oc-lambda-store-production"
    $key = "ProvisionCLI.zip"
    $localPath = "./ProvisionCLI.zip"

    Copy-S3Object -BucketName $bucket -Key $key -LocalFile $localPath
    Expand-Archive -LiteralPath "./ProvisionCLI.zip" -DestinationPath "./ProvisionCLI"
}

function InsertOrUpdateAutomationConfiguration($Database, $ParameterName) {
    return @"
    USE $Database
    IF NOT EXISTS (SELECT * FROM Configuration WHERE ParameterName = '$ParameterName')
    BEGIN
        INSERT INTO Configuration
        SELECT * FROM $copyFromTenantCode..[Configuration]
        WHERE ParameterName = '$ParameterName'
    END
    ELSE
    BEGIN
        UPDATE c
        SET c.ParameterValue = a.ParameterValue
        FROM Configuration c
        INNER JOIN $copyFromTenantCode..[Configuration] a ON a.ParameterName = c.ParameterName
        WHERE c.ParameterName = '$ParameterName'
    END
"@
}

#Ensure that script will exit on an error
$ErrorActionPreference = "Stop"

#First check if the codebase is valid.  If not fail the step which will stop the build.
checkCodeBase $codebase

#Set the variables for the tenant type.
setupTenantTypeVariables $tenantType

[string] $TopLevelDomain = ".connectedcommunity.org"
[string] $SiteName = $domain + $build;
[string] $tk = "";
[string] $WWWSiteURL = $SiteName + $TopLevelDomain;

# Call the function to grab the AWS credentials.
grabAWSCredentials -TENANT_SQL_SERVER $TENANT_SQL_SERVER -SQL_DB $SQL_DB -addressSQLDEVHA01 $addressSQLDEVHA01 -addressSQLDEV03 $addressSQLDEV03

# Update UserID and Pass with the values from the global variables
#This is the IP address for the server sqldevha01.
[string] $Server = $addressSQLDEVHA01
$sqldevha01 = $addressSQLDEVHA01
$sqldev03 = $addressSQLDEV03
$UserID = $global:UserID
$Pass = $global:Pass

[int] $provisionAttempts = 0;
do {
    try {
        Write-Host("Provisioning site $SiteName.");
        PrepProvisionCLITool;
        Push-Location ./ProvisionCLI/publish;
        chmod +x ./ProvisionCLI
        & "./ProvisionCLI" "--tenantcode" $SiteName "--oc" "--modelkey" $modelKey "--productkeys" $productKeys;
        Pop-Location;
        break;
    }
    catch {
        if ($provisionAttempts -eq 3) {
            Write-Host("Error during provisioning. Exceeded maximum number of attempts. Exiting script.");
            throw $_.Exception;
        }
        else {
            Write-Host("Error during provisioning.  Will try again in 1 minute.");
            Start-Sleep -s 60;
            $provisionAttempts++;
        }
    }
} while ($provisionAttempts -lt 3);

#Use TenantKey to check if the TenantApplicationSteps for the site have completed.  If the FinishedOn value is null
#then the step has not completed so the site is not ready.  Pulse to until all steps are complete.
[string] $Database = "TENANT_ACTUAL";
[string] $CreateSiteStatusQuery = $("
    USE " + $Database + "
    SELECT Finished, TenantKey FROM TenantApplication WHERE TenantCode = '" + $SiteName + "'
");
[bool] $finished = 0;
[int] $attempts = 0;
while ((!$finished) -and ($attempts -lt 20)) {
    Write-Host ("Checking TenantApplicationSteps status.");
    $SiteStatusResults = New-Object System.Data.DataTable;
    $SiteStatusResults = ExecuteSqlQuery $Server $Database $CreateSiteStatusQuery $UserId $Pass;
    $finished = $SiteStatusResults.Finished;
    $tk = $SiteStatusResults.TenantKey;
    if (!$finished) {
        Write-Host ("TenantApplication Setup not yet complete. Will try again in 5 seconds.");
        Start-Sleep -s 15;
        $attempts++;
    }
}
if ($attempts -eq 20) {
    throw New-Object System.TimeoutException "Error: TenantApplicationSteps did not complete in time. Exiting script.";
}
Write-Host ("TenantApplicationSteps completed.");
Write-Host ("TenantKey: " + $tk);

#After the steps are complete the proxy still needs to be configured.  There are sometimes issues where the proxy gets hung up
#so the following is protection in that we check the status code of the site and insure that it is 200.
#[string] $url = $SiteName + ".connectedcommunity.org";
#$WebResponse = Invoke-WebRequest $url;
#while($WebResponse.ParsedHtml.title.Contains("Down For Maintenance")){
#    Write-Host ("Waiting for site to come up.");
#    Start-Sleep -s 60;
#}
#Write-Host ("Site provisioning is complete.");

#Update the js/css for the site.
[string] $UpdateAllowedRequestSchemas = $("
    USE " + $Database + "
    UPDATE TenantDomain
    SET AllowedRequestSchemas = '2'
    WHERE SiteDomain like '%" + $SiteName + "%'
");
Write-Host("Updating JS/CSS for the site." );
ExecuteSqlQuery $Server $Database $UpdateAllowedRequestSchemas $UserId $Pass;

#Update the codebase
[string] $CodeBaseKey = "9AAD2E5F-A00A-45E3-86CB-28A19D123C53"; #Default to stable.
[string] $UpdateCodebaseQuery = $("
    UPDATE " + $Database + "..Tenant
    SET CodeBaseKey = '" + $CodeBaseKey + "'
    WHERE TenantKey =  '" + $tk + "'
")
Write-Host("Updating site codebase.");
ExecuteSqlQuery $Server $Database $UpdateCodebaseQuery $UserId $Pass;

if (($tenantType -eq "newThriveTenant") -or ($tenantType -eq "newMembershipTenant")) {
    #Set the AIFeatureKey GUID for Library Bulk Upload with AI. The BulkUploadEnabled parameter is no longer used.
    # Generate a random GUID
    $guid = [guid]::NewGuid()
    [string]$InsertBulkUploadEnabled = $("
        INSERT INTO TenantAIFeature (TenantAIFeatureKey, TenantKey, AIFeatureKey)
        VALUES ('$guid', '$tk','DCFBA222-0C0C-4ED0-95FF-6E9A661C05A4')
    ")
    Write-Host("Enabling Bulk Upload.");
    $SetBulkUploadEnabledResults = New-Object System.Data.DataTable;
    $SetBulkUploadEnabledResults = ExecuteSqlQuery $Server $Database $InsertBulkUploadEnabled $UserId $Pass;

    #Set the AIFeatureKey GUID for AI Formal Tag Suggestions. The EnablePrimary and EnableSuggestedTags parameter are no longer used.
    # Generate a random GUID
    $guid = [guid]::NewGuid()
    [string]$InsertTagSuggestionsEnabled = $("
        INSERT INTO TenantAIFeature (TenantAIFeatureKey, TenantKey, AIFeatureKey)
        VALUES ('$guid', '$tk','42BD4C36-DF63-48B9-A92D-CAAEE473EED3')
    ")
    Write-Host("Enabling AI Formal Tag Suggestions.");
    $SetTagSuggestionsEnabledResults = New-Object System.Data.DataTable;
    $SetTagSuggestionsEnabledResults = ExecuteSqlQuery $Server $Database $InsertTagSuggestionsEnabled $UserId $Pass;
}



$Server = $sqldev03;
$Database = $SiteName;
#Update the menu type enum
[string] $UpdateMenuTypeEnum = $("
    UPDATE " + $SiteName + "..Microsite
    SET MenuTypeEnum = '3'
    WHERE SiteTitle = '" + $SiteName + "'
")
Write-Host("Updating menu type.");
ExecuteSqlQuery $Server $Database $UpdateMenuTypeEnum $UserId $Pass;


#Copy the AddressKey from Automation DB to the new site DB.
[string] $InsertUserAddressQuery = $(
"INSERT INTO [" + $Database + "]..Address
     SELECT a.* FROM $copyFromTenantCode..Address a inner join $copyFromTenantCode..Contact c ON a.AddressKey = c.AddressKey
     WHERE $copyContactWhereClause"
);
Write-Host("Copying address info from Automation database to new site database.");
$AddressResults = New-Object System.Data.DataTable;
$AddressResults = ExecuteSqlQuery $Server $Database $InsertUserAddressQuery $UserId $Pass;


#Copy the contact information needed from the Automation DB to the new site DB.
[string] $InsertUserContactQuery = $("
    INSERT INTO " + $Database + "..Contact
    SELECT * FROM $copyFromTenantCode..Contact
    WHERE $copyContactWhereClause"
);
Write-Host("Copying contacts from Automation database to new site database.");
$ContactResults = New-Object System.Data.DataTable;
$ContactResults = ExecuteSqlQuery $Server $Database $InsertUserContactQuery $UserId $Pass;

#Set the moderated status for the moderated user.
[string] $InsertModerateUserQuery = $("
    INSERT INTO " + $Database + "..members_
    SELECT * FROM $copyFromTenantCode..members_
    WHERE
    FullName_ = 'moderateduser Test'"
);
Write-Host("Setting moderated user.");
$ModeratedUserResults = New-Object System.Data.DataTable;
$ModeratedUserResults = ExecuteSqlQuery $Server $Database $InsertModerateUserQuery $UserId $Pass;

#Set the privacy default settings to all on and all members only.
[string] $PrivacyDefaultSetting = $("
    USE " + $Database + "
    UPDATE ProfileSectionRef
    SET IsActive = '1', DefaultProfilePreferenceEnum = '2'
");
Write-Host("Setting privacy defaults for user.");
$PrivacyDefaultSettingResults = New-Object System.Data.DataTable;
$PrivacyDefaultSettingResults = ExecuteSqlQuery $Server $Database $PrivacyDefaultSetting $UserId $Pass;


#Disable the login recaptcha.
[string] $InsertDisableRecaptchaQuery = InsertOrUpdateAutomationConfiguration $Database "DisableRecaptcha";
Write-Host("Turning off recaptcha for login.");
$RecaptchaResults = New-Object System.Data.DataTable;
$RecaptchaResults = ExecuteSqlQuery $Server $Database $InsertDisableRecaptchaQuery $UserId $Pass;

# Disable Moxie Manager for Blogs
[string] $InsertDisableMoxieForBlogs = InsertOrUpdateAutomationConfiguration $Database "DisableMoxieManagerForBlogs";
Write-Host("Disabling Moxie Manager for Blogs.");
$MoxieManagerResults = New-Object System.Data.DataTable;
$MoxieManagerResults = ExecuteSqlQuery $Server $Database $InsertDisableMoxieForBlogs $UserId $Pass;

#Set the isAutomation flag to True.  This turns off the development border as it causes issues when the tests run.
[string] $InsertIsAutomationQuery = InsertOrUpdateAutomationConfiguration $Database "IsAutomation";
Write-Host("Turning on isAutomation.");
$IsAutomationResults = New-Object System.Data.DataTable;
$IsAutomationResults = ExecuteSqlQuery $Server $Database $InsertIsAutomationQuery $UserId $Pass;

#Set the ModerateFrequentContributions flag to False.  This removes the posting restrictions for a user.
[string] $UpdateModerateFrequentContributionsQuery = InsertOrUpdateAutomationConfiguration $Database "ModerateFrequentContributions"
Write-Host("Turning off moderation restrictions.");
$ModerationRestrictionsResults = New-Object System.Data.DataTable;
$ModerationRestrictionsResults = ExecuteSqlQuery $Server $Database $UpdateModerateFrequentContributionsQuery $UserId $Pass;

#Set the BypassScheduledDeletion flag to True.  This allows for access to an OC tenant that is scheduled for deletion.
[string] $UpdateBypassScheduledDeletionQuery = InsertOrUpdateAutomationConfiguration $Database "BypassScheduledDeletion"
Write-Host("Enabling Deletion Bypassing");
$BypassDeletionResults = New-Object System.Data.DataTable;
$BypassDeletionResults = ExecuteSqlQuery $Server $Database $UpdateBypassScheduledDeletionQuery $UserId $Pass;

#Set the IsRedirectUrlValidated flag to False.  This turns off the URL redirect validation that will break the ads tests.
[string] $UpdateRedirectUrlValidationQuery = $("
    USE " + $Database + "
    UPDATE Configuration
    SET ParameterValue = 'False'
	WHERE ParameterName =  'IsRedirectUrlValidated'
");
Write-Host("Turning off URL redirect validation.");
$UpdateRedirectUrlValidationResults = New-Object System.Data.DataTable;
$UpdateRedirectUrlValidationResults = ExecuteSqlQuery $Server $Database $UpdateRedirectUrlValidationQuery $UserId $Pass;

#Set the DistributeCacheInvalidation flag to True.  This updates the caching issues caused by the CCAdmin decoupling project.
[string] $InsertDistributeCacheInvalidationQuery = InsertOrUpdateAutomationConfiguration $Database "DistributeCacheInvalidation"
#Write-Host("Turning on DistributeCacheInvalidation.");
#$IsAutomationResults = New-Object System.Data.DataTable;
#$IsAutomationResults = ExecuteSqlQuery $Server $Database $InsertDistributeCacheInvalidationQuery $UserId $Pass;

#Set the volunteer points per hour.
[string] $SetVolunteerPointsPerHour = InsertOrUpdateAutomationConfiguration $Database "VolunteerPointsPerHour"
Write-Host("Updating volunteer points per hour.");
$SetVolunteerPointsPerHourResults = New-Object System.Data.DataTable;
$SetVolunteerPointsPerHourResults = ExecuteSqlQuery $Server $Database $SetVolunteerPointsPerHour $UserId $Pass;

#Set the HasSmartNewsletter flag to True.  This enables Smart Newsletter feature.
[string] $InsertHasSmartNewsletter = InsertOrUpdateAutomationConfiguration $Database "HasSmartNewsletter"
Write-Host("Enabling Smart Newsletter.");
$SetHasSmartNewsletterResults = New-Object System.Data.DataTable;
$SetHasSmartNewsletterResults = ExecuteSqlQuery $Server $Database $InsertHasSmartNewsletter $UserId $Pass;

#Update LoginSessionLengthMinutes to 720 for 12 hour sessions. This value is copied from Automation Tenant.
[string] $UpdateLoginSessionLengthMinutesQuery = InsertOrUpdateAutomationConfiguration $Database "LoginSessionLengthMinutes"
Write-Host("Updating LoginSessionLengthMinutes to 720 minutes.");
$UpdateLoginSessionLengthMinutesResults = New-Object System.Data.DataTable;
$UpdateLoginSessionLengthMinutesResults = ExecuteSqlQuery $Server $Database $UpdateLoginSessionLengthMinutesQuery $UserId $Pass;

#Set UseRibbitRibbons to True.
[string] $InsertUseRibbitRibbons = InsertOrUpdateAutomationConfiguration $Database "UseRibbitRibbons"
Write-Host("Enabling new ribbons and badges.");
$SetUseRibbitRibbonsResults = New-Object System.Data.DataTable;
$SetUseRibbitRibbonsResults = ExecuteSqlQuery $Server $Database $InsertUseRibbitRibbons $UserId $Pass;

#Set ShowShareWidgetOnEventDetails to True.
[string] $UpdateShowShareWidgetOnEventDetailsQuery = $("
    USE " + $Database + "
    UPDATE Configuration
    SET ParameterValue = 'True'
    WHERE ParameterName = 'ShowShareWidgetOnEventDetails'
")
Write-Host("Enabling social sharing for Events.");
$UpdateShowShareWidgetOnEventResults = New-Object System.Data.DataTable;
$UpdateShowShareWidgetOnEventResults = ExecuteSqlQuery $Server $Database $UpdateShowShareWidgetOnEventDetailsQuery $UserId $Pass;

#Set ShowShareWidgetOnOpportunityDetails to True.
[string] $UpdateShowShareWidgetOnOpportunityDetailsQuery = $("
    USE " + $Database + "
    UPDATE Configuration
    SET ParameterValue = 'True'
    WHERE ParameterName = 'ShowShareWidgetOnOpportunityDetails'
")
Write-Host("Enabling social sharing for Volunteer Opportunities.");
$UpdateShowShareWidgetOnOpportunityResults = New-Object System.Data.DataTable;
$UpdateShowShareWidgetOnOpportunityResults = ExecuteSqlQuery $Server $Database $UpdateShowShareWidgetOnOpportunityDetailsQuery $UserId $Pass;

#Turn off the requires acceptance setting for terms and conditions.
[string] $UpdateTermsandConditionsQuery = $("
    UPDATE " + $Database + "..TermsAndConditions
    SET RequireAcceptance = '0'
    WHERE TermsAndConditionsKey =  '02786337-EAAB-4BB8-A426-E444A7494098'
")
Write-Host("Turning off terms and conditions from being required.");
$TermsAndConditionsResults = New-Object System.Data.DataTable;
$TermsAndConditionsResults = ExecuteSqlQuery $Server $Database $UpdateTermsandConditionsQuery $UserId $Pass;


if (($tenantType -eq "newThriveTenant") -or ($tenantType -eq "newMembershipTenant")) {
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
        SELECT * FROM $copyFromTenantCode..SecurityGroupRef
        WHERE SecurityGroupKey = 'CBAC2F7E-AB7F-4ED3-A28A-01835B30466A'
    " );
    Write-Host("Copying security group reference for members.");
    $SecurityGroupResults = New-Object System.Data.DataTable;
    $SecurityGroupResults = ExecuteSqlQuery $Server $Database $InsertMemberSecurityGroup $UserId $Pass;

    #Copy the AddressKey of Companies from Thriveautomation DB to the new site DB.
    [string]$InsertCompaniesAddressQuery = $(
    "INSERT INTO [" + $Database + "]..Address
        SELECT a.* FROM $copyFromTenantCode..Address a inner join $copyFromTenantCode..Contact c ON a.AddressKey = c.AddressKey
        WHERE CompanyName in ('Better Business Bureau','Small Business Majority');"
    );
    Write-Host("Copying address of Companies info from Thriveautomation database to new site database.");
    $CompaniesAddressResults = New-Object System.Data.DataTable;
    $CompaniesAddressResults = ExecuteSqlQuery $Server $Database $InsertCompaniesAddressQuery $UserId $Pass;

    #Copy the companies information needed from the Thriveautomation DB to the new site DB.
    [string]$InsertCompaniesContactQuery = $( "
        INSERT INTO " + $Database + "..Contact
        SELECT * FROM $copyFromTenantCode..Contact
        WHERE
        EmailAddress in ('bbb@higherlogic.test','sbm@higherlogic.test')"
    );
    Write-Host("Copying companies from Thriveautomation database to new site database.");
    $CompaniesResults = New-Object System.Data.DataTable;
    $CompaniesResults = ExecuteSqlQuery $Server $Database $InsertCompaniesContactQuery $UserId $Pass;

    #Copy ContactSecurityGroup.
    [string]$InsertContactSecurityGroup = $( "
        INSERT INTO ContactSecurityGroup
        SELECT * FROM $copyFromTenantCode..ContactSecurityGroup
        WHERE SecurityGroupKey = 'CBAC2F7E-AB7F-4ED3-A28A-01835B30466A'
    " );
    Write-Host("Copying ContactSecurityGroup.");
    $ContactSecurityGroupResults = New-Object System.Data.DataTable;
    $ContactSecurityGroupResults = ExecuteSqlQuery $Server $Database $InsertContactSecurityGroup $UserId $Pass;

}

$Server = $sqldevha01;
$Database = "TENANT_ACTUAL";

# Create a new row in the IAM table for tenant being provisioned by copying an existing row in the DB and replacing with a new random IamKey and TenantKey
[string] $IamKey = New-Guid;
[string] $CommunityMemberKey = New-Guid;
[string] $CommunityAdminKey = New-Guid;
[string] $CopyIAMKeyQuery = $("
  INSERT INTO IAM (IAMKey, TenantKey, ContactKey, Description, ActiveFlag, CreatedOn, Password, CreatedByContactKey)
  SELECT '" + $IamKey + "', '" + $tk + "', ContactKey, Description, ActiveFlag, GETDATE(), Password, '7F2DE571-92E8-49B0-BA12-27413BF99C95'
  FROM IAM
  WHERE TenantKey = '2E5C40E5-CF39-4960-819E-DD33436DF7C0' AND ContactKey = '1C5AFED2-9C2C-41E5-9882-97B0AD38343B'
");
Write-Host("Creating new super admin row in IAM table");
$CopyIAMKeyQueryResults = New-Object System.Data.DataTable;
$CopyIAMKeyQueryResults = ExecuteSqlQuery $Server $Database $CopyIAMKeyQuery $UserId $Pass;

[string] $CopyCommunityMemberKeyQuery = $("
  INSERT INTO IAM (IAMKey, TenantKey, ContactKey, Description, ActiveFlag, CreatedOn, Password, CreatedByContactKey)
  SELECT '" + $CommunityMemberKey + "', '" + $tk + "', ContactKey, Description, ActiveFlag, GETDATE(), Password, '7F2DE571-92E8-49B0-BA12-27413BF99C95'
  FROM IAM
  WHERE TenantKey = '2E5C40E5-CF39-4960-819E-DD33436DF7C0' AND ContactKey = 'd6a8e870-a1da-4416-a96d-e3e76dd16e56'
");
Write-Host("Creating new community member row in IAM table");
$CopyCommunityMemberKeyQueryResults = New-Object System.Data.DataTable;
$CopyCommunityMemberKeyQueryResults = ExecuteSqlQuery $Server $Database $CopyCommunityMemberKeyQuery $UserId $Pass;

[string] $CopyCommunityAdminKeyQuery = $("
  INSERT INTO IAM (IAMKey, TenantKey, ContactKey, Description, ActiveFlag, CreatedOn, Password, CreatedByContactKey)
  SELECT '" + $CommunityAdminKey + "', '" + $tk + "', ContactKey, Description, ActiveFlag, GETDATE(), Password, '7F2DE571-92E8-49B0-BA12-27413BF99C95'
  FROM IAM
  WHERE TenantKey = '2E5C40E5-CF39-4960-819E-DD33436DF7C0' AND ContactKey = '9fd2dc9d-f3ea-4484-8320-457adc2495d8'
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
$propertiesContents = @"
superadminKey=$RetrievedIAMKey
superadminPassword=$Description
communitymemberKey=$RetrievedCommunityMemberKey
communitymemberPassword=$DescriptionCommunityMember
communityadminKey=$RetrievedCommunityAdminKey
communityadminPassword=$DescriptionCommunityAdmin
"@

$propertiesContents | Out-File -FilePath $propertiesFilePath -Encoding utf8

Write-Host "Properties file created at: $propertiesFilePath"
Write-Host "Properties file contents: `n$propertiesContents"

#Set the AIFeatureKey GUID for Library Bulk Upload with AI. The BulkUploadEnabled parameter is no longer used.
# Generate a random GUID
$guid = [guid]::NewGuid()
[string] $InsertBulkUploadEnabled = $("
    INSERT INTO TenantAIFeature (TenantAIFeatureKey, TenantKey, AIFeatureKey)
    VALUES ('$guid', '$tk','DCFBA222-0C0C-4ED0-95FF-6E9A661C05A4')
")
Write-Host("Enabling Bulk Upload.");
$SetBulkUploadEnabledResults = New-Object System.Data.DataTable;
$SetBulkUploadEnabledResults = ExecuteSqlQuery $Server $Database $InsertBulkUploadEnabled $UserId $Pass;

#Set the AIFeatureKey GUID for AI Formal Tag Suggestions. The EnablePrimary and EnableSuggestedTags parameter are no longer used.
# Generate a random GUID
$guid = [guid]::NewGuid()
[string] $InsertTagSuggestionsEnabled = $("
    INSERT INTO TenantAIFeature (TenantAIFeatureKey, TenantKey, AIFeatureKey)
    VALUES ('$guid', '$tk','42BD4C36-DF63-48B9-A92D-CAAEE473EED3')
")
Write-Host("Enabling AI Formal Tag Suggestions.");
$SetTagSuggestionsEnabledResults = New-Object System.Data.DataTable;
$SetTagSuggestionsEnabledResults = ExecuteSqlQuery $Server $Database $InsertTagSuggestionsEnabled $UserId $Pass;


#Copy the Ad Test menu and pages from Automation DB to the new site DB.
$Server = $sqldev03;
$Database = $SiteName;
[string] $RunCopyAllNavigationFromTenantStoredProcedure = "CopyAllNavigationFromTenant";
Write-Host("Copying menus from Automation menu to new site menu.");
$CopyNavigationResults = New-Object System.Data.DataTable;
$CopyNavigationResults = ExecuteSqlQueryWithParameters $Server $Database $RunCopyAllNavigationFromTenantStoredProcedure $UserId $Pass "menu";

#Update queue route suffix table accordingly with sproc.
#[string] $UpdateQueueRouteSuffixForTenantStoredProcedure = "sp_RouteQueues";
#Write-Host("Updating queue route suffix.");
#$UpdateQueueRouteResults = New-Object System.Data.DataTable;
#$UpdateQueueRouteResults = ExecuteSqlQueryWithParameters $Server $Database $UpdateQueueRouteSuffixForTenantStoredProcedure $UserId $Pass "queue";

#Wait until site is reachable
[bool] $siteReachable = 0;
[int] $attempts = 0;
while (!$siteReachable) {
    Write-Host ("Checking site status.");
    $Url = 'https://' + $SiteName + '.connectedcommunity.org';
    try {
        $request = Invoke-WebRequest $Url -UseBasicParsing -UseDefaultCredentials -Headers @{ 'HL_Test' = $codebase }
        [int]$StatusCode = $request.StatusCode
    }
    catch {
        [int]$StatusCode = $_.Exception.Response.StatusCode
    }
    if ($StatusCode -eq 200) {
        $siteReachable = 1;
        Write-Host ("Site is reachable.");
    }
    else {
        Write-Host ("Site is not yet reachable. Will try again in 10 seconds.");
        $attempts++;
        # if it's been more than 10 minutes, something is almost certainly wrong
        if ($attempts -eq 60) {
            throw New-Object System.TimeoutException "Error: Site did not come up in time. Exiting script.";
        }
        Start-Sleep -s 10;
    }
}


Write-Host("Script run to completion.");