package cn.mysic.json;

import java.io.Serializable;

/**
 * Created by liuchuan on 12/9/16.
 */
public class Constents implements Serializable {

        public  String API_BASE = "/api";
        // int
        public  int signaturePollingInterval = 1000;
        public  int deployTimeout = 10;
        public  int incidentsCount = 4000;
        public  int maxPayloadLength = 1510;
        public  int maxPacketLength = 1518;
        public  int defaultCheckDiskUsageInterval = 300000;
        public  int defaultDiskUsageWarningThreshold = 80;
        public  int diskUsageWarningThresholdLowerLimit = 1;
        public  int diskUsageWarningThresholdUpperLimit = 99;
        public  int defaultCheckDiskUsageIntervalLowerLimit = 10000;
        public  int defaultCheckDiskUsageIntervalUpperLimit = 60000;
        public  int defaultIdleTime = 5;//5 minutes
        public  int defaultMaxLoginTries = 5; //5 times
        public  int defaultCheckIpTrafficInterval = 60; //60 seconds
        public  int defaultLogDeletionInterval = -1; //never delete
        //    public  String defaultLogDeletionTime = "14:00"; //the time to delete logs
        public  String defaultNotificationType = "visual_audio";
        public  String defaultNotificationTarget = "incident";
        public  String DEVICE_CHECK_INTERVAL_KEY = "deviceCheckInterval";
        public static boolean defaultEncryptionOption = false;
        public static boolean defaultAutoMWBackup = false;
        public static boolean defaultAutoDPIBackup = false;

        // strings
        public  String defaultIpLoginRuleData = "0.0.0.0/0";
        public  String defaultPasswordComplexity = "中";
        public  String lastDeployedPolicy = "last-deployed-policy";
        public  String lastDeployedSignature = "last-deployed-signature";
        public  String managementConsoleName = "安全监管平台";
        public  String consoleImageExt = ".bin";

        // setup api paths
        public  String setupApiRootPath = "http://localhost:1337/setup/api";
        public  String changeIpApiPath = setupApiRootPath + "/network";
        public  String updateEventApiPath = setupApiRootPath + "/event";
        public  String systemChownApiPath = setupApiRootPath + "/system/chown";
        public  String networkINFApiPath = setupApiRootPath + "/network_inf";
        public  String allNicsApiPath = setupApiRootPath + "/network_inf/nics";
        public  String ntpApiPath = setupApiRootPath + "/ntp";
        public  String dhcpApiPath = setupApiRootPath + "/dhcp";
        public  String factoryResetMwPath = setupApiRootPath + "/factoryreset/mw";
        public  String restartMwPath = setupApiRootPath + "/restart/mw";
        public  String poweroffMwPath = setupApiRootPath + "/poweroff/mw";
        public  String remoteIpHostnamePath = setupApiRootPath + "/hostfile";
        public  String systemConfigPath = setupApiRootPath + "/config";
        public  String restartMwProcessPath = setupApiRootPath + "/restart_process";
        public  String shutdownMwPath = setupApiRootPath + "/poweroff/mw";
        public  String mwSystemStatPath = setupApiRootPath + "/systemstat";
        public  String upgradeConsolePath = setupApiRootPath + "/upgrade_console";
        public  String systemFileDeleteApiPath = setupApiRootPath + "/system/delete";
        public  String routingTableApiPath = setupApiRootPath + "/network/routing";

        public  String IMAGE_COPY_DPI = setupApiRootPath + "/image/dpi/copy";

        public  String IMAGE_DPI_UPGRADE_START = setupApiRootPath + "/image/dpi/upgrade/start";

        public  String IMAGE_DPI_UPGRADE_STOP = setupApiRootPath + "/image/dpi/upgrade/stop";

        public  String storageManagementStrategyName = "存储管理";
        public  String ipTrafficManagementStrategyName = "IpTraffic管理";
        public  String ipTrafficToggleStrategyName = "IpTraffic切换";
        public  String logManagementStrategyName = "日志管理";
        public  String ipLoginManagementStrategyName = "IP登录管理";
        public  String timeoutManagementStrategyName = "超时管理";
        public  String maxLoginTriesManagementStrategyName = "最大登录次数管理";
        public  String pwdComplexityManagementStrategyName = "密码强度管理";
        public  String notificationManagementStrategyName = "通知管理";
        public  String encryptionManagementStrategyName = "加密管理";
        public  String autoMWBackupManagementStrategyName = "MW自动备份";
        public  String autoDPIBackupManagementStrategyName = "DPI自动备份";
//    public  String logDeletionStrategyName = "日志数据删除";
//    public  String logDeletionTimeStrategyName = "日志数据删除时间";

        public  String strategyRuleGtName = "大于";
        public  String strategyRuleGeName = "大于等于";
        public  String strategyRuleLeName = "小于等于";
        public  String strategyRuleLtName = "小于";
        public  String strategyRuleEqName = "等于";
        public  String strategyRuleLikeName = "类似";

        public  String strategyActionDenyName = "拒绝";
        public  String strategyActionDeleteName = "删除";
        public  String strategyActionAlertName = "告警";
        public  String strategyActionTimeoutName = "超时";
        public  String strategyActionBackupName = "备份";
        public  String strategyActionAllowName = "允许";

        // paths
        public  String deployedRuleRootPath = "/data/rule";
        public  String destinationPolicyPath = "/usr/local/etc/suricata/rules/whitelist";
        public  String destinationSignaturePath = "/usr/local/etc/suricata/rules/signature";
        public  String dpiPath = "/tmp/tmp_upgrade/setup-dpi.bin";
        public  String generatedPolicyPath = "/tmp";
        public  String generatedSignaturePath = generatedPolicyPath;
        public  String emptyPolicyPath = "/data/signature/temp/empty.rules";
        public  String factoryResetFilePath = "/data/cornerstone_setup/factory_reset";
        public  String home = System.getenv("HOME");
        public  String policy = "policy";
        public  String username = "root";
        public  String serialNumberPath = "/data/serial_number";
        public  String signature = "signature";
        public  String signaturePath = "/data/signature";
        public  String SYSLOG_HOST_FILE = "/data/sw/syslog_host";
        public  String PRESERVE_LOG_DIR = "/preserve/logs";
        public  String UPSTART_LOG_DIR = "/var/log/upstart";

        public  String tempFilepath = "/data/tempUploadedSig.rules";
        public  String SCRIPT_PATH = "/usr/share/server/bin";
        public  String KAFKA_METADATA_BROKER_LIST ="metadata.broker.list";
        public  String KAFKA_REQUEST_REQUIRED_ACKS = "request.required.acks";
        public  String ZOOKEEPER_LIST="zookeeper.zookeeperList";
        public  String ZOOKEEPER_CONNECTION_TIMEOUT="zookeeper.connectionTimeout";
        public  String ZOOKEEPER_SESSION_TIMEOUT="zookeeper.sessionTimeout";
        public  String ZOOKEEPER_REMOTE_LIST = "zookeeper.remoteZookeeperList";
        public  String KAFKA_REMOTE_LIST = "kafka.remoteBrokerList";

        public  String REST_SERVER_PORT = "restserver.port";
        public  String CORNERSTONE_ENVIRONMENT = "cornerstone.environment";
        public  String LEARNING_INTERVAL = "cornerstone.learningIntervalInSeconds";
        public  String BEHAVIOR_INTERVAL = "cornerstone.behaviorIntervalInSeconds";
        public  String ZOOKEEPER_GROUP_ID = "zookeeper.groupId";
        public  Integer maxLimit = 1000000;

        public  String  POLICY_TYPE = "policy";

        // MDC keys for logging
        public  String USER_AS_MDC_KEY = "user";
        public  String IP_AS_MDC_KEY = "ip";

        // LOG dir
        public  String SYSTEM_PROPERTY_LOG_DIR = "log_dir";
        public  String LOG_DIR = System.getProperty(SYSTEM_PROPERTY_LOG_DIR);

        public  String RULE_DEPLOYMENT_RESPONSE_TIMEOUT = "rule.deployment.response.timeout";

        public  int RULE_DEPLOYMENT_RESPONSE_DEFAULT_TIMEOUT = 30;

        public  String ZOOKEEPER_BOXES = "zookeeper.boxes.path";

        public  String ZOOKEEPER_BOXES_REQUEST = "zookeeper.boxes.request";

        public  String DPI_SIGNATURE_FOLDER = "dpi.signature.folder";

        public  String DEFAULT_SIGNATURE_FOLDER = "/data/dpi/rules/signature-p0-p1";

        public  String ENGINE_WRAPPER_PATH="dpi.engine.wrapper";

        public  String DEFAULT_ENGINE_WRAPPER="/usr/share/cornerstone_setup/main/engine_wrapper.pyc";

        public  String RESTART_ARG = "restart";

        public  String START_ARG = "start";

        public  String STOP_ARG = "stop";

        public  String CAT_ARG = "cat";

        public  String PYTHON_PATH="PYTHONPATH";

        public  String PYTHON_PATH_VALUE="/usr/share/cornerstone_setup";

        public  String ENGINE_WRAPPER_TIMEOUT="dpi.engine.timeout";
        public  int ENGINE_WRAPPER_TIMEOUT_VALUE=30;

        public  String PYTHON_CMD_KEY="env.python";
        public  String PYTHON_CMD_PATH="/usr/bin/python";

        /** key to port status path */
        public  String PORT_STATUS_PATH_KEY = "port.status.path";

        /** key to port status operate path */
        public  String PORT_STATUS_OP_PATH_KEY = "port.status.op.path";

        public  String PORT_STATUS_PATH = "/sys/class/net/agl1/carrier";

        public  String PORT_STATUS_OPERATE_PATH = "/sys/class/net/agl1/operstate";

        public  String DPI_WARNING_THRESHOLD="dpi.warning_threshold";
        public  double DPI_WARNING_THRESHOLD_VALUE=0.8;

        public  String SYSTEM_ROLE= "com/istuary/usermanagement/targetaction/SystemRole.json";
        public  String SECURITY_ROLE= "com/istuary/usermanagement/targetaction/SecurityRole.json";
        public  String AUDIT_ROLE = "com/istuary/usermanagement/targetaction/AuditRole.json";
        public  String DOMAIN_ADMIN_ROLE = "com/istuary/usermanagement/targetaction/DomainRole.json";

        public  double NODEX=-100;
        public  double NODEY=0;

        // console image validation constants
        public  byte[] CONSOLE_BIN_VERSION =new byte[]{(byte)0x47, (byte)0xBB};
        public  int CONSOLE_BIN_META_DATA_LENGTH = 4096;
        public  int CONSOLE_CHECKSUM_LEGNTH = 1024;


        // dpi image validation constants
        public  byte[] BIN_VERSION1 =new byte[]{(byte)0x47, (byte)0xBC};
        public  int BIN_META_DATA_LENGTH = 4096;
        public  int CHECKSUM_LEGNTH = 1024;

        //version code
        public  String ALL_IN_ONE_VERSION = "X00";
        public  String REMOTE_VERSION = "X01";
        public  String STANDALONE_VERSION = "X02";

        public  String GENERAL_VERSION = "C00";
        public  String GUIDAO_VERSION = "C01";
        public  String BAOXIN_VERSION = "C02";
        public  String ALL_IN_ONE_SECURITY_KEY = "istuary1118";

        public  String MONITORING_AUDIT_VERSION = "JA00";

        public  String ftpProtocolToggleStrategyName = "FTP协议切换";
        public  String PROTOCOL_FTP_NAME = "ftp";

        public  String DEVICE_LOG = "deviceLog"; // name of deviceLog

        public  String DEPLOYMENT_LOG = "deploymentLog"; // name of deploymentLog

        public  String INCIDENT_LOG = "incidentLog"; // name of incidentLog

        public  String DIANLI_VERSION = "C05";

        public  String ICG_VERSION = "GW00";
        public  String ZOOKEEPER_ENCRYPTION = "zookeeper.encryption";

        public  String KAFKA_ENCRYPTION = "kafka.encryption";

        public  String DPI_ENCRYPTION = "dpi.encryption";

        /**
         * Key for the server instance id in the configuration.
         * App will use this as the key to pass in the server instance id
         * where we write deviceLog we will use this value as the serverInstanceId in the log
         */
        public  String SERVER_INSTANCE_ID = "serverInstanceId";

        public  int DEVICE_CHECK_INTERVAL = 3000; // interval for checking device(milliseconds)


        public  String MERGE_INTERVAL_KEY="mergeIntervalInSeconds";

        public  String CONCURRENT_THREADS = "concurrentThreads";

        public  String MAXIMUM_QUEUE_SIZE = "maximumQueueSize";

        //MW debug info
        public  String MW_DEBUGINFO = "debuginfo.path";
        public  String MW_DEBUGINFO_VALUE = "/preserve/debuginfocollect";
        public  String MW_MYSQLDUMP = "debuginfo.mysqldump";
        public  String MW_MYSQLDUMP_VALUE = "off";
        //backup info
        public  String BACKUPINFO = "backupinfo.path";
        public  String BACKUPINFO_VALUE = "/preserve/backupinfocollect";
        public  String BACKUP_MYSQLDUMP = "backupinfo.mysqldump";
        public  String BACKUP_MYSQLDUMP_VALUE = "off";
        public  String BACKUP_FILECOUNT = "backupinfo.count";
        public  int BACKUP_FILECOUNT_VALUE = 50;
        public  String MW_TEMP_PATH = "/tmp";
        //MW  call python api
        public  String kafkadebuginfo = setupApiRootPath + "/debug/kafka";
        public  String systemdebuginfo = setupApiRootPath + "/debug/system";
        public  String mysqldebuginfo = setupApiRootPath + "/debug/db";
        public  int MW_DEBUGINFO_TASKTIMEOUT = 1800;

        public  String logcopydebuginfo = setupApiRootPath + "/debug/log";

        //DPI debug info
        public  int DPI_DEBUGINFO_RESPONSETIMEOUT = 60*5;
        public  int DPI_DEBUGINFO_TASKTIMEOUT = 1800;
        public  String DPI_DEBUGINFO_ISCOREDUMP = "debuginfo.isCoredump";
        public  String DPI_DEBUGINFO_ISCOREDUMP_VALUE = "false";
        //staticRoutingTableInfoTimeout
        public  int DPI_ROUTERSETTING_RESPONSETIMEOUT = 60;
        public  int DPI_ROUTERINFO_RESPONSETIMEOUT = 30;
        //
        public  String USERPERMISSION  ="dpi_user";
        public  String GROUPERMISSION ="dpi_user";
        //mw日志收集权限
        public  String SWRUNPERMISSION  ="swrun";
        public  String SWRUNGPERMISSION ="swrun";
        //backupinfo
        public  String mysqlbackupinfo = setupApiRootPath + "/backup/db";
        public  String systemdbackupinfo = setupApiRootPath + "/backup/system";

        //Solve enumeration transformation
        public  String IPRULES = "IPRULES";
        public  String IP_RULE = "IP_RULE";
        //backupmysqltable
        public  String []mysqlsystable = {"SchedulingJobs","SchedulingJobMetas","PrivateProtocols","PrivateProtocolDatas","ProtocolSwitchRecords",
                "DpiSubscriptions","StrategyActionOptions","StrategyActions","StrategyActionSets","StrategyInfos","StrategyOptions",
                "StrategyRuleOptions","StrategyRules","StrategyRuleSets",
                "PrivilegeTargets","PrivilegeTargetActions","PrivilegeActions","RoleDeviceLinks","RolePrivileges","Roles",
                "UserRoleLinks","Users","UserWidgetOptionSettings","WidgetOptions",
                "RoleSpecificMenus","StructureSafetyNodes","SystemSettings"
        };
        public  String []mysqlbustable ={"DevicePorts","DeviceGroups","DeviceProperties","Devices","DeviceACLLinks","DeviceACLs","DeviceProductLinks",
                "RemoteRoutings","Nodes","Links","NodeViews",
                "Policies","PolicyBlocks","PolicyBlockSignatureLinks","PolicyCounts","Rules","DomainInfos","LearnedIpMacs",
                "Signatures","MaliciousDomains","DomainSubnets","DomainRoleLinks",
                "TodoLists","Topologies", "TopologyJsons","NodeRuleLinks", "NodeSignatureLinks","Models","NodeZones","NodeProtocolLinks",
                "AttackTargets","AttackPaths","PortRoutings","Products","ProductVulnerabilities","Vulnerabilities"
        };

        //get last time incident
        public  String lasthour = "hour";
        public  String lastday = "day";
        public  String lastweek = "week";

    public String getAPI_BASE() {
        return API_BASE;
    }

    public void setAPI_BASE(String API_BASE) {
        this.API_BASE = API_BASE;
    }

    public int getSignaturePollingInterval() {
        return signaturePollingInterval;
    }

    public void setSignaturePollingInterval(int signaturePollingInterval) {
        this.signaturePollingInterval = signaturePollingInterval;
    }

    public int getDeployTimeout() {
        return deployTimeout;
    }

    public void setDeployTimeout(int deployTimeout) {
        this.deployTimeout = deployTimeout;
    }

    public int getIncidentsCount() {
        return incidentsCount;
    }

    public void setIncidentsCount(int incidentsCount) {
        this.incidentsCount = incidentsCount;
    }

    public int getMaxPayloadLength() {
        return maxPayloadLength;
    }

    public void setMaxPayloadLength(int maxPayloadLength) {
        this.maxPayloadLength = maxPayloadLength;
    }

    public int getMaxPacketLength() {
        return maxPacketLength;
    }

    public void setMaxPacketLength(int maxPacketLength) {
        this.maxPacketLength = maxPacketLength;
    }

    public int getDefaultCheckDiskUsageInterval() {
        return defaultCheckDiskUsageInterval;
    }

    public void setDefaultCheckDiskUsageInterval(int defaultCheckDiskUsageInterval) {
        this.defaultCheckDiskUsageInterval = defaultCheckDiskUsageInterval;
    }

    public int getDefaultDiskUsageWarningThreshold() {
        return defaultDiskUsageWarningThreshold;
    }

    public void setDefaultDiskUsageWarningThreshold(int defaultDiskUsageWarningThreshold) {
        this.defaultDiskUsageWarningThreshold = defaultDiskUsageWarningThreshold;
    }

    public int getDiskUsageWarningThresholdLowerLimit() {
        return diskUsageWarningThresholdLowerLimit;
    }

    public void setDiskUsageWarningThresholdLowerLimit(int diskUsageWarningThresholdLowerLimit) {
        this.diskUsageWarningThresholdLowerLimit = diskUsageWarningThresholdLowerLimit;
    }

    public int getDiskUsageWarningThresholdUpperLimit() {
        return diskUsageWarningThresholdUpperLimit;
    }

    public void setDiskUsageWarningThresholdUpperLimit(int diskUsageWarningThresholdUpperLimit) {
        this.diskUsageWarningThresholdUpperLimit = diskUsageWarningThresholdUpperLimit;
    }

    public int getDefaultCheckDiskUsageIntervalLowerLimit() {
        return defaultCheckDiskUsageIntervalLowerLimit;
    }

    public void setDefaultCheckDiskUsageIntervalLowerLimit(int defaultCheckDiskUsageIntervalLowerLimit) {
        this.defaultCheckDiskUsageIntervalLowerLimit = defaultCheckDiskUsageIntervalLowerLimit;
    }

    public int getDefaultCheckDiskUsageIntervalUpperLimit() {
        return defaultCheckDiskUsageIntervalUpperLimit;
    }

    public void setDefaultCheckDiskUsageIntervalUpperLimit(int defaultCheckDiskUsageIntervalUpperLimit) {
        this.defaultCheckDiskUsageIntervalUpperLimit = defaultCheckDiskUsageIntervalUpperLimit;
    }

    public int getDefaultIdleTime() {
        return defaultIdleTime;
    }

    public void setDefaultIdleTime(int defaultIdleTime) {
        this.defaultIdleTime = defaultIdleTime;
    }

    public int getDefaultMaxLoginTries() {
        return defaultMaxLoginTries;
    }

    public void setDefaultMaxLoginTries(int defaultMaxLoginTries) {
        this.defaultMaxLoginTries = defaultMaxLoginTries;
    }

    public int getDefaultCheckIpTrafficInterval() {
        return defaultCheckIpTrafficInterval;
    }

    public void setDefaultCheckIpTrafficInterval(int defaultCheckIpTrafficInterval) {
        this.defaultCheckIpTrafficInterval = defaultCheckIpTrafficInterval;
    }

    public int getDefaultLogDeletionInterval() {
        return defaultLogDeletionInterval;
    }

    public void setDefaultLogDeletionInterval(int defaultLogDeletionInterval) {
        this.defaultLogDeletionInterval = defaultLogDeletionInterval;
    }

    public String getDefaultNotificationType() {
        return defaultNotificationType;
    }

    public void setDefaultNotificationType(String defaultNotificationType) {
        this.defaultNotificationType = defaultNotificationType;
    }

    public String getDefaultNotificationTarget() {
        return defaultNotificationTarget;
    }

    public void setDefaultNotificationTarget(String defaultNotificationTarget) {
        this.defaultNotificationTarget = defaultNotificationTarget;
    }

    public String getDEVICE_CHECK_INTERVAL_KEY() {
        return DEVICE_CHECK_INTERVAL_KEY;
    }

    public void setDEVICE_CHECK_INTERVAL_KEY(String DEVICE_CHECK_INTERVAL_KEY) {
        this.DEVICE_CHECK_INTERVAL_KEY = DEVICE_CHECK_INTERVAL_KEY;
    }

    public static boolean isDefaultEncryptionOption() {
        return defaultEncryptionOption;
    }

    public static void setDefaultEncryptionOption(boolean defaultEncryptionOption) {
        Constents.defaultEncryptionOption = defaultEncryptionOption;
    }

    public static boolean isDefaultAutoMWBackup() {
        return defaultAutoMWBackup;
    }

    public static void setDefaultAutoMWBackup(boolean defaultAutoMWBackup) {
        Constents.defaultAutoMWBackup = defaultAutoMWBackup;
    }

    public static boolean isDefaultAutoDPIBackup() {
        return defaultAutoDPIBackup;
    }

    public static void setDefaultAutoDPIBackup(boolean defaultAutoDPIBackup) {
        Constents.defaultAutoDPIBackup = defaultAutoDPIBackup;
    }

    public String getDefaultIpLoginRuleData() {
        return defaultIpLoginRuleData;
    }

    public void setDefaultIpLoginRuleData(String defaultIpLoginRuleData) {
        this.defaultIpLoginRuleData = defaultIpLoginRuleData;
    }

    public String getDefaultPasswordComplexity() {
        return defaultPasswordComplexity;
    }

    public void setDefaultPasswordComplexity(String defaultPasswordComplexity) {
        this.defaultPasswordComplexity = defaultPasswordComplexity;
    }

    public String getLastDeployedPolicy() {
        return lastDeployedPolicy;
    }

    public void setLastDeployedPolicy(String lastDeployedPolicy) {
        this.lastDeployedPolicy = lastDeployedPolicy;
    }

    public String getLastDeployedSignature() {
        return lastDeployedSignature;
    }

    public void setLastDeployedSignature(String lastDeployedSignature) {
        this.lastDeployedSignature = lastDeployedSignature;
    }

    public String getManagementConsoleName() {
        return managementConsoleName;
    }

    public void setManagementConsoleName(String managementConsoleName) {
        this.managementConsoleName = managementConsoleName;
    }

    public String getConsoleImageExt() {
        return consoleImageExt;
    }

    public void setConsoleImageExt(String consoleImageExt) {
        this.consoleImageExt = consoleImageExt;
    }

    public String getSetupApiRootPath() {
        return setupApiRootPath;
    }

    public void setSetupApiRootPath(String setupApiRootPath) {
        this.setupApiRootPath = setupApiRootPath;
    }

    public String getChangeIpApiPath() {
        return changeIpApiPath;
    }

    public void setChangeIpApiPath(String changeIpApiPath) {
        this.changeIpApiPath = changeIpApiPath;
    }

    public String getUpdateEventApiPath() {
        return updateEventApiPath;
    }

    public void setUpdateEventApiPath(String updateEventApiPath) {
        this.updateEventApiPath = updateEventApiPath;
    }

    public String getSystemChownApiPath() {
        return systemChownApiPath;
    }

    public void setSystemChownApiPath(String systemChownApiPath) {
        this.systemChownApiPath = systemChownApiPath;
    }

    public String getNetworkINFApiPath() {
        return networkINFApiPath;
    }

    public void setNetworkINFApiPath(String networkINFApiPath) {
        this.networkINFApiPath = networkINFApiPath;
    }

    public String getAllNicsApiPath() {
        return allNicsApiPath;
    }

    public void setAllNicsApiPath(String allNicsApiPath) {
        this.allNicsApiPath = allNicsApiPath;
    }

    public String getNtpApiPath() {
        return ntpApiPath;
    }

    public void setNtpApiPath(String ntpApiPath) {
        this.ntpApiPath = ntpApiPath;
    }

    public String getDhcpApiPath() {
        return dhcpApiPath;
    }

    public void setDhcpApiPath(String dhcpApiPath) {
        this.dhcpApiPath = dhcpApiPath;
    }

    public String getFactoryResetMwPath() {
        return factoryResetMwPath;
    }

    public void setFactoryResetMwPath(String factoryResetMwPath) {
        this.factoryResetMwPath = factoryResetMwPath;
    }

    public String getRestartMwPath() {
        return restartMwPath;
    }

    public void setRestartMwPath(String restartMwPath) {
        this.restartMwPath = restartMwPath;
    }

    public String getPoweroffMwPath() {
        return poweroffMwPath;
    }

    public void setPoweroffMwPath(String poweroffMwPath) {
        this.poweroffMwPath = poweroffMwPath;
    }

    public String getRemoteIpHostnamePath() {
        return remoteIpHostnamePath;
    }

    public void setRemoteIpHostnamePath(String remoteIpHostnamePath) {
        this.remoteIpHostnamePath = remoteIpHostnamePath;
    }

    public String getSystemConfigPath() {
        return systemConfigPath;
    }

    public void setSystemConfigPath(String systemConfigPath) {
        this.systemConfigPath = systemConfigPath;
    }

    public String getRestartMwProcessPath() {
        return restartMwProcessPath;
    }

    public void setRestartMwProcessPath(String restartMwProcessPath) {
        this.restartMwProcessPath = restartMwProcessPath;
    }

    public String getShutdownMwPath() {
        return shutdownMwPath;
    }

    public void setShutdownMwPath(String shutdownMwPath) {
        this.shutdownMwPath = shutdownMwPath;
    }

    public String getMwSystemStatPath() {
        return mwSystemStatPath;
    }

    public void setMwSystemStatPath(String mwSystemStatPath) {
        this.mwSystemStatPath = mwSystemStatPath;
    }

    public String getUpgradeConsolePath() {
        return upgradeConsolePath;
    }

    public void setUpgradeConsolePath(String upgradeConsolePath) {
        this.upgradeConsolePath = upgradeConsolePath;
    }

    public String getSystemFileDeleteApiPath() {
        return systemFileDeleteApiPath;
    }

    public void setSystemFileDeleteApiPath(String systemFileDeleteApiPath) {
        this.systemFileDeleteApiPath = systemFileDeleteApiPath;
    }

    public String getRoutingTableApiPath() {
        return routingTableApiPath;
    }

    public void setRoutingTableApiPath(String routingTableApiPath) {
        this.routingTableApiPath = routingTableApiPath;
    }

    public String getIMAGE_COPY_DPI() {
        return IMAGE_COPY_DPI;
    }

    public void setIMAGE_COPY_DPI(String IMAGE_COPY_DPI) {
        this.IMAGE_COPY_DPI = IMAGE_COPY_DPI;
    }

    public String getIMAGE_DPI_UPGRADE_START() {
        return IMAGE_DPI_UPGRADE_START;
    }

    public void setIMAGE_DPI_UPGRADE_START(String IMAGE_DPI_UPGRADE_START) {
        this.IMAGE_DPI_UPGRADE_START = IMAGE_DPI_UPGRADE_START;
    }

    public String getIMAGE_DPI_UPGRADE_STOP() {
        return IMAGE_DPI_UPGRADE_STOP;
    }

    public void setIMAGE_DPI_UPGRADE_STOP(String IMAGE_DPI_UPGRADE_STOP) {
        this.IMAGE_DPI_UPGRADE_STOP = IMAGE_DPI_UPGRADE_STOP;
    }

    public String getStorageManagementStrategyName() {
        return storageManagementStrategyName;
    }

    public void setStorageManagementStrategyName(String storageManagementStrategyName) {
        this.storageManagementStrategyName = storageManagementStrategyName;
    }

    public String getIpTrafficManagementStrategyName() {
        return ipTrafficManagementStrategyName;
    }

    public void setIpTrafficManagementStrategyName(String ipTrafficManagementStrategyName) {
        this.ipTrafficManagementStrategyName = ipTrafficManagementStrategyName;
    }

    public String getIpTrafficToggleStrategyName() {
        return ipTrafficToggleStrategyName;
    }

    public void setIpTrafficToggleStrategyName(String ipTrafficToggleStrategyName) {
        this.ipTrafficToggleStrategyName = ipTrafficToggleStrategyName;
    }

    public String getLogManagementStrategyName() {
        return logManagementStrategyName;
    }

    public void setLogManagementStrategyName(String logManagementStrategyName) {
        this.logManagementStrategyName = logManagementStrategyName;
    }

    public String getIpLoginManagementStrategyName() {
        return ipLoginManagementStrategyName;
    }

    public void setIpLoginManagementStrategyName(String ipLoginManagementStrategyName) {
        this.ipLoginManagementStrategyName = ipLoginManagementStrategyName;
    }

    public String getTimeoutManagementStrategyName() {
        return timeoutManagementStrategyName;
    }

    public void setTimeoutManagementStrategyName(String timeoutManagementStrategyName) {
        this.timeoutManagementStrategyName = timeoutManagementStrategyName;
    }

    public String getMaxLoginTriesManagementStrategyName() {
        return maxLoginTriesManagementStrategyName;
    }

    public void setMaxLoginTriesManagementStrategyName(String maxLoginTriesManagementStrategyName) {
        this.maxLoginTriesManagementStrategyName = maxLoginTriesManagementStrategyName;
    }

    public String getPwdComplexityManagementStrategyName() {
        return pwdComplexityManagementStrategyName;
    }

    public void setPwdComplexityManagementStrategyName(String pwdComplexityManagementStrategyName) {
        this.pwdComplexityManagementStrategyName = pwdComplexityManagementStrategyName;
    }

    public String getNotificationManagementStrategyName() {
        return notificationManagementStrategyName;
    }

    public void setNotificationManagementStrategyName(String notificationManagementStrategyName) {
        this.notificationManagementStrategyName = notificationManagementStrategyName;
    }

    public String getEncryptionManagementStrategyName() {
        return encryptionManagementStrategyName;
    }

    public void setEncryptionManagementStrategyName(String encryptionManagementStrategyName) {
        this.encryptionManagementStrategyName = encryptionManagementStrategyName;
    }

    public String getAutoMWBackupManagementStrategyName() {
        return autoMWBackupManagementStrategyName;
    }

    public void setAutoMWBackupManagementStrategyName(String autoMWBackupManagementStrategyName) {
        this.autoMWBackupManagementStrategyName = autoMWBackupManagementStrategyName;
    }

    public String getAutoDPIBackupManagementStrategyName() {
        return autoDPIBackupManagementStrategyName;
    }

    public void setAutoDPIBackupManagementStrategyName(String autoDPIBackupManagementStrategyName) {
        this.autoDPIBackupManagementStrategyName = autoDPIBackupManagementStrategyName;
    }

    public String getStrategyRuleGtName() {
        return strategyRuleGtName;
    }

    public void setStrategyRuleGtName(String strategyRuleGtName) {
        this.strategyRuleGtName = strategyRuleGtName;
    }

    public String getStrategyRuleGeName() {
        return strategyRuleGeName;
    }

    public void setStrategyRuleGeName(String strategyRuleGeName) {
        this.strategyRuleGeName = strategyRuleGeName;
    }

    public String getStrategyRuleLeName() {
        return strategyRuleLeName;
    }

    public void setStrategyRuleLeName(String strategyRuleLeName) {
        this.strategyRuleLeName = strategyRuleLeName;
    }

    public String getStrategyRuleLtName() {
        return strategyRuleLtName;
    }

    public void setStrategyRuleLtName(String strategyRuleLtName) {
        this.strategyRuleLtName = strategyRuleLtName;
    }

    public String getStrategyRuleEqName() {
        return strategyRuleEqName;
    }

    public void setStrategyRuleEqName(String strategyRuleEqName) {
        this.strategyRuleEqName = strategyRuleEqName;
    }

    public String getStrategyRuleLikeName() {
        return strategyRuleLikeName;
    }

    public void setStrategyRuleLikeName(String strategyRuleLikeName) {
        this.strategyRuleLikeName = strategyRuleLikeName;
    }

    public String getStrategyActionDenyName() {
        return strategyActionDenyName;
    }

    public void setStrategyActionDenyName(String strategyActionDenyName) {
        this.strategyActionDenyName = strategyActionDenyName;
    }

    public String getStrategyActionDeleteName() {
        return strategyActionDeleteName;
    }

    public void setStrategyActionDeleteName(String strategyActionDeleteName) {
        this.strategyActionDeleteName = strategyActionDeleteName;
    }

    public String getStrategyActionAlertName() {
        return strategyActionAlertName;
    }

    public void setStrategyActionAlertName(String strategyActionAlertName) {
        this.strategyActionAlertName = strategyActionAlertName;
    }

    public String getStrategyActionTimeoutName() {
        return strategyActionTimeoutName;
    }

    public void setStrategyActionTimeoutName(String strategyActionTimeoutName) {
        this.strategyActionTimeoutName = strategyActionTimeoutName;
    }

    public String getStrategyActionBackupName() {
        return strategyActionBackupName;
    }

    public void setStrategyActionBackupName(String strategyActionBackupName) {
        this.strategyActionBackupName = strategyActionBackupName;
    }

    public String getStrategyActionAllowName() {
        return strategyActionAllowName;
    }

    public void setStrategyActionAllowName(String strategyActionAllowName) {
        this.strategyActionAllowName = strategyActionAllowName;
    }

    public String getDeployedRuleRootPath() {
        return deployedRuleRootPath;
    }

    public void setDeployedRuleRootPath(String deployedRuleRootPath) {
        this.deployedRuleRootPath = deployedRuleRootPath;
    }

    public String getDestinationPolicyPath() {
        return destinationPolicyPath;
    }

    public void setDestinationPolicyPath(String destinationPolicyPath) {
        this.destinationPolicyPath = destinationPolicyPath;
    }

    public String getDestinationSignaturePath() {
        return destinationSignaturePath;
    }

    public void setDestinationSignaturePath(String destinationSignaturePath) {
        this.destinationSignaturePath = destinationSignaturePath;
    }

    public String getDpiPath() {
        return dpiPath;
    }

    public void setDpiPath(String dpiPath) {
        this.dpiPath = dpiPath;
    }

    public String getGeneratedPolicyPath() {
        return generatedPolicyPath;
    }

    public void setGeneratedPolicyPath(String generatedPolicyPath) {
        this.generatedPolicyPath = generatedPolicyPath;
    }

    public String getGeneratedSignaturePath() {
        return generatedSignaturePath;
    }

    public void setGeneratedSignaturePath(String generatedSignaturePath) {
        this.generatedSignaturePath = generatedSignaturePath;
    }

    public String getEmptyPolicyPath() {
        return emptyPolicyPath;
    }

    public void setEmptyPolicyPath(String emptyPolicyPath) {
        this.emptyPolicyPath = emptyPolicyPath;
    }

    public String getFactoryResetFilePath() {
        return factoryResetFilePath;
    }

    public void setFactoryResetFilePath(String factoryResetFilePath) {
        this.factoryResetFilePath = factoryResetFilePath;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSerialNumberPath() {
        return serialNumberPath;
    }

    public void setSerialNumberPath(String serialNumberPath) {
        this.serialNumberPath = serialNumberPath;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }

    public String getSYSLOG_HOST_FILE() {
        return SYSLOG_HOST_FILE;
    }

    public void setSYSLOG_HOST_FILE(String SYSLOG_HOST_FILE) {
        this.SYSLOG_HOST_FILE = SYSLOG_HOST_FILE;
    }

    public String getPRESERVE_LOG_DIR() {
        return PRESERVE_LOG_DIR;
    }

    public void setPRESERVE_LOG_DIR(String PRESERVE_LOG_DIR) {
        this.PRESERVE_LOG_DIR = PRESERVE_LOG_DIR;
    }

    public String getUPSTART_LOG_DIR() {
        return UPSTART_LOG_DIR;
    }

    public void setUPSTART_LOG_DIR(String UPSTART_LOG_DIR) {
        this.UPSTART_LOG_DIR = UPSTART_LOG_DIR;
    }

    public String getTempFilepath() {
        return tempFilepath;
    }

    public void setTempFilepath(String tempFilepath) {
        this.tempFilepath = tempFilepath;
    }

    public String getSCRIPT_PATH() {
        return SCRIPT_PATH;
    }

    public void setSCRIPT_PATH(String SCRIPT_PATH) {
        this.SCRIPT_PATH = SCRIPT_PATH;
    }

    public String getKAFKA_METADATA_BROKER_LIST() {
        return KAFKA_METADATA_BROKER_LIST;
    }

    public void setKAFKA_METADATA_BROKER_LIST(String KAFKA_METADATA_BROKER_LIST) {
        this.KAFKA_METADATA_BROKER_LIST = KAFKA_METADATA_BROKER_LIST;
    }

    public String getKAFKA_REQUEST_REQUIRED_ACKS() {
        return KAFKA_REQUEST_REQUIRED_ACKS;
    }

    public void setKAFKA_REQUEST_REQUIRED_ACKS(String KAFKA_REQUEST_REQUIRED_ACKS) {
        this.KAFKA_REQUEST_REQUIRED_ACKS = KAFKA_REQUEST_REQUIRED_ACKS;
    }

    public String getZOOKEEPER_LIST() {
        return ZOOKEEPER_LIST;
    }

    public void setZOOKEEPER_LIST(String ZOOKEEPER_LIST) {
        this.ZOOKEEPER_LIST = ZOOKEEPER_LIST;
    }

    public String getZOOKEEPER_CONNECTION_TIMEOUT() {
        return ZOOKEEPER_CONNECTION_TIMEOUT;
    }

    public void setZOOKEEPER_CONNECTION_TIMEOUT(String ZOOKEEPER_CONNECTION_TIMEOUT) {
        this.ZOOKEEPER_CONNECTION_TIMEOUT = ZOOKEEPER_CONNECTION_TIMEOUT;
    }

    public String getZOOKEEPER_SESSION_TIMEOUT() {
        return ZOOKEEPER_SESSION_TIMEOUT;
    }

    public void setZOOKEEPER_SESSION_TIMEOUT(String ZOOKEEPER_SESSION_TIMEOUT) {
        this.ZOOKEEPER_SESSION_TIMEOUT = ZOOKEEPER_SESSION_TIMEOUT;
    }

    public String getZOOKEEPER_REMOTE_LIST() {
        return ZOOKEEPER_REMOTE_LIST;
    }

    public void setZOOKEEPER_REMOTE_LIST(String ZOOKEEPER_REMOTE_LIST) {
        this.ZOOKEEPER_REMOTE_LIST = ZOOKEEPER_REMOTE_LIST;
    }

    public String getKAFKA_REMOTE_LIST() {
        return KAFKA_REMOTE_LIST;
    }

    public void setKAFKA_REMOTE_LIST(String KAFKA_REMOTE_LIST) {
        this.KAFKA_REMOTE_LIST = KAFKA_REMOTE_LIST;
    }

    public String getREST_SERVER_PORT() {
        return REST_SERVER_PORT;
    }

    public void setREST_SERVER_PORT(String REST_SERVER_PORT) {
        this.REST_SERVER_PORT = REST_SERVER_PORT;
    }

    public String getCORNERSTONE_ENVIRONMENT() {
        return CORNERSTONE_ENVIRONMENT;
    }

    public void setCORNERSTONE_ENVIRONMENT(String CORNERSTONE_ENVIRONMENT) {
        this.CORNERSTONE_ENVIRONMENT = CORNERSTONE_ENVIRONMENT;
    }

    public String getLEARNING_INTERVAL() {
        return LEARNING_INTERVAL;
    }

    public void setLEARNING_INTERVAL(String LEARNING_INTERVAL) {
        this.LEARNING_INTERVAL = LEARNING_INTERVAL;
    }

    public String getBEHAVIOR_INTERVAL() {
        return BEHAVIOR_INTERVAL;
    }

    public void setBEHAVIOR_INTERVAL(String BEHAVIOR_INTERVAL) {
        this.BEHAVIOR_INTERVAL = BEHAVIOR_INTERVAL;
    }

    public String getZOOKEEPER_GROUP_ID() {
        return ZOOKEEPER_GROUP_ID;
    }

    public void setZOOKEEPER_GROUP_ID(String ZOOKEEPER_GROUP_ID) {
        this.ZOOKEEPER_GROUP_ID = ZOOKEEPER_GROUP_ID;
    }

    public Integer getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Integer maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getPOLICY_TYPE() {
        return POLICY_TYPE;
    }

    public void setPOLICY_TYPE(String POLICY_TYPE) {
        this.POLICY_TYPE = POLICY_TYPE;
    }

    public String getUSER_AS_MDC_KEY() {
        return USER_AS_MDC_KEY;
    }

    public void setUSER_AS_MDC_KEY(String USER_AS_MDC_KEY) {
        this.USER_AS_MDC_KEY = USER_AS_MDC_KEY;
    }

    public String getIP_AS_MDC_KEY() {
        return IP_AS_MDC_KEY;
    }

    public void setIP_AS_MDC_KEY(String IP_AS_MDC_KEY) {
        this.IP_AS_MDC_KEY = IP_AS_MDC_KEY;
    }

    public String getSYSTEM_PROPERTY_LOG_DIR() {
        return SYSTEM_PROPERTY_LOG_DIR;
    }

    public void setSYSTEM_PROPERTY_LOG_DIR(String SYSTEM_PROPERTY_LOG_DIR) {
        this.SYSTEM_PROPERTY_LOG_DIR = SYSTEM_PROPERTY_LOG_DIR;
    }

    public String getLOG_DIR() {
        return LOG_DIR;
    }

    public void setLOG_DIR(String LOG_DIR) {
        this.LOG_DIR = LOG_DIR;
    }

    public String getRULE_DEPLOYMENT_RESPONSE_TIMEOUT() {
        return RULE_DEPLOYMENT_RESPONSE_TIMEOUT;
    }

    public void setRULE_DEPLOYMENT_RESPONSE_TIMEOUT(String RULE_DEPLOYMENT_RESPONSE_TIMEOUT) {
        this.RULE_DEPLOYMENT_RESPONSE_TIMEOUT = RULE_DEPLOYMENT_RESPONSE_TIMEOUT;
    }

    public int getRULE_DEPLOYMENT_RESPONSE_DEFAULT_TIMEOUT() {
        return RULE_DEPLOYMENT_RESPONSE_DEFAULT_TIMEOUT;
    }

    public void setRULE_DEPLOYMENT_RESPONSE_DEFAULT_TIMEOUT(int RULE_DEPLOYMENT_RESPONSE_DEFAULT_TIMEOUT) {
        this.RULE_DEPLOYMENT_RESPONSE_DEFAULT_TIMEOUT = RULE_DEPLOYMENT_RESPONSE_DEFAULT_TIMEOUT;
    }

    public String getZOOKEEPER_BOXES() {
        return ZOOKEEPER_BOXES;
    }

    public void setZOOKEEPER_BOXES(String ZOOKEEPER_BOXES) {
        this.ZOOKEEPER_BOXES = ZOOKEEPER_BOXES;
    }

    public String getZOOKEEPER_BOXES_REQUEST() {
        return ZOOKEEPER_BOXES_REQUEST;
    }

    public void setZOOKEEPER_BOXES_REQUEST(String ZOOKEEPER_BOXES_REQUEST) {
        this.ZOOKEEPER_BOXES_REQUEST = ZOOKEEPER_BOXES_REQUEST;
    }

    public String getDPI_SIGNATURE_FOLDER() {
        return DPI_SIGNATURE_FOLDER;
    }

    public void setDPI_SIGNATURE_FOLDER(String DPI_SIGNATURE_FOLDER) {
        this.DPI_SIGNATURE_FOLDER = DPI_SIGNATURE_FOLDER;
    }

    public String getDEFAULT_SIGNATURE_FOLDER() {
        return DEFAULT_SIGNATURE_FOLDER;
    }

    public void setDEFAULT_SIGNATURE_FOLDER(String DEFAULT_SIGNATURE_FOLDER) {
        this.DEFAULT_SIGNATURE_FOLDER = DEFAULT_SIGNATURE_FOLDER;
    }

    public String getENGINE_WRAPPER_PATH() {
        return ENGINE_WRAPPER_PATH;
    }

    public void setENGINE_WRAPPER_PATH(String ENGINE_WRAPPER_PATH) {
        this.ENGINE_WRAPPER_PATH = ENGINE_WRAPPER_PATH;
    }

    public String getDEFAULT_ENGINE_WRAPPER() {
        return DEFAULT_ENGINE_WRAPPER;
    }

    public void setDEFAULT_ENGINE_WRAPPER(String DEFAULT_ENGINE_WRAPPER) {
        this.DEFAULT_ENGINE_WRAPPER = DEFAULT_ENGINE_WRAPPER;
    }

    public String getRESTART_ARG() {
        return RESTART_ARG;
    }

    public void setRESTART_ARG(String RESTART_ARG) {
        this.RESTART_ARG = RESTART_ARG;
    }

    public String getSTART_ARG() {
        return START_ARG;
    }

    public void setSTART_ARG(String START_ARG) {
        this.START_ARG = START_ARG;
    }

    public String getSTOP_ARG() {
        return STOP_ARG;
    }

    public void setSTOP_ARG(String STOP_ARG) {
        this.STOP_ARG = STOP_ARG;
    }

    public String getCAT_ARG() {
        return CAT_ARG;
    }

    public void setCAT_ARG(String CAT_ARG) {
        this.CAT_ARG = CAT_ARG;
    }

    public String getPYTHON_PATH() {
        return PYTHON_PATH;
    }

    public void setPYTHON_PATH(String PYTHON_PATH) {
        this.PYTHON_PATH = PYTHON_PATH;
    }

    public String getPYTHON_PATH_VALUE() {
        return PYTHON_PATH_VALUE;
    }

    public void setPYTHON_PATH_VALUE(String PYTHON_PATH_VALUE) {
        this.PYTHON_PATH_VALUE = PYTHON_PATH_VALUE;
    }

    public String getENGINE_WRAPPER_TIMEOUT() {
        return ENGINE_WRAPPER_TIMEOUT;
    }

    public void setENGINE_WRAPPER_TIMEOUT(String ENGINE_WRAPPER_TIMEOUT) {
        this.ENGINE_WRAPPER_TIMEOUT = ENGINE_WRAPPER_TIMEOUT;
    }

    public int getENGINE_WRAPPER_TIMEOUT_VALUE() {
        return ENGINE_WRAPPER_TIMEOUT_VALUE;
    }

    public void setENGINE_WRAPPER_TIMEOUT_VALUE(int ENGINE_WRAPPER_TIMEOUT_VALUE) {
        this.ENGINE_WRAPPER_TIMEOUT_VALUE = ENGINE_WRAPPER_TIMEOUT_VALUE;
    }

    public String getPYTHON_CMD_KEY() {
        return PYTHON_CMD_KEY;
    }

    public void setPYTHON_CMD_KEY(String PYTHON_CMD_KEY) {
        this.PYTHON_CMD_KEY = PYTHON_CMD_KEY;
    }

    public String getPYTHON_CMD_PATH() {
        return PYTHON_CMD_PATH;
    }

    public void setPYTHON_CMD_PATH(String PYTHON_CMD_PATH) {
        this.PYTHON_CMD_PATH = PYTHON_CMD_PATH;
    }

    public String getPORT_STATUS_PATH_KEY() {
        return PORT_STATUS_PATH_KEY;
    }

    public void setPORT_STATUS_PATH_KEY(String PORT_STATUS_PATH_KEY) {
        this.PORT_STATUS_PATH_KEY = PORT_STATUS_PATH_KEY;
    }

    public String getPORT_STATUS_OP_PATH_KEY() {
        return PORT_STATUS_OP_PATH_KEY;
    }

    public void setPORT_STATUS_OP_PATH_KEY(String PORT_STATUS_OP_PATH_KEY) {
        this.PORT_STATUS_OP_PATH_KEY = PORT_STATUS_OP_PATH_KEY;
    }

    public String getPORT_STATUS_PATH() {
        return PORT_STATUS_PATH;
    }

    public void setPORT_STATUS_PATH(String PORT_STATUS_PATH) {
        this.PORT_STATUS_PATH = PORT_STATUS_PATH;
    }

    public String getPORT_STATUS_OPERATE_PATH() {
        return PORT_STATUS_OPERATE_PATH;
    }

    public void setPORT_STATUS_OPERATE_PATH(String PORT_STATUS_OPERATE_PATH) {
        this.PORT_STATUS_OPERATE_PATH = PORT_STATUS_OPERATE_PATH;
    }

    public String getDPI_WARNING_THRESHOLD() {
        return DPI_WARNING_THRESHOLD;
    }

    public void setDPI_WARNING_THRESHOLD(String DPI_WARNING_THRESHOLD) {
        this.DPI_WARNING_THRESHOLD = DPI_WARNING_THRESHOLD;
    }

    public double getDPI_WARNING_THRESHOLD_VALUE() {
        return DPI_WARNING_THRESHOLD_VALUE;
    }

    public void setDPI_WARNING_THRESHOLD_VALUE(double DPI_WARNING_THRESHOLD_VALUE) {
        this.DPI_WARNING_THRESHOLD_VALUE = DPI_WARNING_THRESHOLD_VALUE;
    }

    public String getSYSTEM_ROLE() {
        return SYSTEM_ROLE;
    }

    public void setSYSTEM_ROLE(String SYSTEM_ROLE) {
        this.SYSTEM_ROLE = SYSTEM_ROLE;
    }

    public String getSECURITY_ROLE() {
        return SECURITY_ROLE;
    }

    public void setSECURITY_ROLE(String SECURITY_ROLE) {
        this.SECURITY_ROLE = SECURITY_ROLE;
    }

    public String getAUDIT_ROLE() {
        return AUDIT_ROLE;
    }

    public void setAUDIT_ROLE(String AUDIT_ROLE) {
        this.AUDIT_ROLE = AUDIT_ROLE;
    }

    public String getDOMAIN_ADMIN_ROLE() {
        return DOMAIN_ADMIN_ROLE;
    }

    public void setDOMAIN_ADMIN_ROLE(String DOMAIN_ADMIN_ROLE) {
        this.DOMAIN_ADMIN_ROLE = DOMAIN_ADMIN_ROLE;
    }

    public double getNODEX() {
        return NODEX;
    }

    public void setNODEX(double NODEX) {
        this.NODEX = NODEX;
    }

    public double getNODEY() {
        return NODEY;
    }

    public void setNODEY(double NODEY) {
        this.NODEY = NODEY;
    }

    public byte[] getCONSOLE_BIN_VERSION() {
        return CONSOLE_BIN_VERSION;
    }

    public void setCONSOLE_BIN_VERSION(byte[] CONSOLE_BIN_VERSION) {
        this.CONSOLE_BIN_VERSION = CONSOLE_BIN_VERSION;
    }

    public int getCONSOLE_BIN_META_DATA_LENGTH() {
        return CONSOLE_BIN_META_DATA_LENGTH;
    }

    public void setCONSOLE_BIN_META_DATA_LENGTH(int CONSOLE_BIN_META_DATA_LENGTH) {
        this.CONSOLE_BIN_META_DATA_LENGTH = CONSOLE_BIN_META_DATA_LENGTH;
    }

    public int getCONSOLE_CHECKSUM_LEGNTH() {
        return CONSOLE_CHECKSUM_LEGNTH;
    }

    public void setCONSOLE_CHECKSUM_LEGNTH(int CONSOLE_CHECKSUM_LEGNTH) {
        this.CONSOLE_CHECKSUM_LEGNTH = CONSOLE_CHECKSUM_LEGNTH;
    }

    public byte[] getBIN_VERSION1() {
        return BIN_VERSION1;
    }

    public void setBIN_VERSION1(byte[] BIN_VERSION1) {
        this.BIN_VERSION1 = BIN_VERSION1;
    }

    public int getBIN_META_DATA_LENGTH() {
        return BIN_META_DATA_LENGTH;
    }

    public void setBIN_META_DATA_LENGTH(int BIN_META_DATA_LENGTH) {
        this.BIN_META_DATA_LENGTH = BIN_META_DATA_LENGTH;
    }

    public int getCHECKSUM_LEGNTH() {
        return CHECKSUM_LEGNTH;
    }

    public void setCHECKSUM_LEGNTH(int CHECKSUM_LEGNTH) {
        this.CHECKSUM_LEGNTH = CHECKSUM_LEGNTH;
    }

    public String getALL_IN_ONE_VERSION() {
        return ALL_IN_ONE_VERSION;
    }

    public void setALL_IN_ONE_VERSION(String ALL_IN_ONE_VERSION) {
        this.ALL_IN_ONE_VERSION = ALL_IN_ONE_VERSION;
    }

    public String getREMOTE_VERSION() {
        return REMOTE_VERSION;
    }

    public void setREMOTE_VERSION(String REMOTE_VERSION) {
        this.REMOTE_VERSION = REMOTE_VERSION;
    }

    public String getSTANDALONE_VERSION() {
        return STANDALONE_VERSION;
    }

    public void setSTANDALONE_VERSION(String STANDALONE_VERSION) {
        this.STANDALONE_VERSION = STANDALONE_VERSION;
    }

    public String getGENERAL_VERSION() {
        return GENERAL_VERSION;
    }

    public void setGENERAL_VERSION(String GENERAL_VERSION) {
        this.GENERAL_VERSION = GENERAL_VERSION;
    }

    public String getGUIDAO_VERSION() {
        return GUIDAO_VERSION;
    }

    public void setGUIDAO_VERSION(String GUIDAO_VERSION) {
        this.GUIDAO_VERSION = GUIDAO_VERSION;
    }

    public String getBAOXIN_VERSION() {
        return BAOXIN_VERSION;
    }

    public void setBAOXIN_VERSION(String BAOXIN_VERSION) {
        this.BAOXIN_VERSION = BAOXIN_VERSION;
    }

    public String getALL_IN_ONE_SECURITY_KEY() {
        return ALL_IN_ONE_SECURITY_KEY;
    }

    public void setALL_IN_ONE_SECURITY_KEY(String ALL_IN_ONE_SECURITY_KEY) {
        this.ALL_IN_ONE_SECURITY_KEY = ALL_IN_ONE_SECURITY_KEY;
    }

    public String getMONITORING_AUDIT_VERSION() {
        return MONITORING_AUDIT_VERSION;
    }

    public void setMONITORING_AUDIT_VERSION(String MONITORING_AUDIT_VERSION) {
        this.MONITORING_AUDIT_VERSION = MONITORING_AUDIT_VERSION;
    }

    public String getFtpProtocolToggleStrategyName() {
        return ftpProtocolToggleStrategyName;
    }

    public void setFtpProtocolToggleStrategyName(String ftpProtocolToggleStrategyName) {
        this.ftpProtocolToggleStrategyName = ftpProtocolToggleStrategyName;
    }

    public String getPROTOCOL_FTP_NAME() {
        return PROTOCOL_FTP_NAME;
    }

    public void setPROTOCOL_FTP_NAME(String PROTOCOL_FTP_NAME) {
        this.PROTOCOL_FTP_NAME = PROTOCOL_FTP_NAME;
    }

    public String getDEVICE_LOG() {
        return DEVICE_LOG;
    }

    public void setDEVICE_LOG(String DEVICE_LOG) {
        this.DEVICE_LOG = DEVICE_LOG;
    }

    public String getDEPLOYMENT_LOG() {
        return DEPLOYMENT_LOG;
    }

    public void setDEPLOYMENT_LOG(String DEPLOYMENT_LOG) {
        this.DEPLOYMENT_LOG = DEPLOYMENT_LOG;
    }

    public String getINCIDENT_LOG() {
        return INCIDENT_LOG;
    }

    public void setINCIDENT_LOG(String INCIDENT_LOG) {
        this.INCIDENT_LOG = INCIDENT_LOG;
    }

    public String getDIANLI_VERSION() {
        return DIANLI_VERSION;
    }

    public void setDIANLI_VERSION(String DIANLI_VERSION) {
        this.DIANLI_VERSION = DIANLI_VERSION;
    }

    public String getICG_VERSION() {
        return ICG_VERSION;
    }

    public void setICG_VERSION(String ICG_VERSION) {
        this.ICG_VERSION = ICG_VERSION;
    }

    public String getZOOKEEPER_ENCRYPTION() {
        return ZOOKEEPER_ENCRYPTION;
    }

    public void setZOOKEEPER_ENCRYPTION(String ZOOKEEPER_ENCRYPTION) {
        this.ZOOKEEPER_ENCRYPTION = ZOOKEEPER_ENCRYPTION;
    }

    public String getKAFKA_ENCRYPTION() {
        return KAFKA_ENCRYPTION;
    }

    public void setKAFKA_ENCRYPTION(String KAFKA_ENCRYPTION) {
        this.KAFKA_ENCRYPTION = KAFKA_ENCRYPTION;
    }

    public String getDPI_ENCRYPTION() {
        return DPI_ENCRYPTION;
    }

    public void setDPI_ENCRYPTION(String DPI_ENCRYPTION) {
        this.DPI_ENCRYPTION = DPI_ENCRYPTION;
    }

    public String getSERVER_INSTANCE_ID() {
        return SERVER_INSTANCE_ID;
    }

    public void setSERVER_INSTANCE_ID(String SERVER_INSTANCE_ID) {
        this.SERVER_INSTANCE_ID = SERVER_INSTANCE_ID;
    }

    public int getDEVICE_CHECK_INTERVAL() {
        return DEVICE_CHECK_INTERVAL;
    }

    public void setDEVICE_CHECK_INTERVAL(int DEVICE_CHECK_INTERVAL) {
        this.DEVICE_CHECK_INTERVAL = DEVICE_CHECK_INTERVAL;
    }

    public String getMERGE_INTERVAL_KEY() {
        return MERGE_INTERVAL_KEY;
    }

    public void setMERGE_INTERVAL_KEY(String MERGE_INTERVAL_KEY) {
        this.MERGE_INTERVAL_KEY = MERGE_INTERVAL_KEY;
    }

    public String getCONCURRENT_THREADS() {
        return CONCURRENT_THREADS;
    }

    public void setCONCURRENT_THREADS(String CONCURRENT_THREADS) {
        this.CONCURRENT_THREADS = CONCURRENT_THREADS;
    }

    public String getMAXIMUM_QUEUE_SIZE() {
        return MAXIMUM_QUEUE_SIZE;
    }

    public void setMAXIMUM_QUEUE_SIZE(String MAXIMUM_QUEUE_SIZE) {
        this.MAXIMUM_QUEUE_SIZE = MAXIMUM_QUEUE_SIZE;
    }

    public String getMW_DEBUGINFO() {
        return MW_DEBUGINFO;
    }

    public void setMW_DEBUGINFO(String MW_DEBUGINFO) {
        this.MW_DEBUGINFO = MW_DEBUGINFO;
    }

    public String getMW_DEBUGINFO_VALUE() {
        return MW_DEBUGINFO_VALUE;
    }

    public void setMW_DEBUGINFO_VALUE(String MW_DEBUGINFO_VALUE) {
        this.MW_DEBUGINFO_VALUE = MW_DEBUGINFO_VALUE;
    }

    public String getMW_MYSQLDUMP() {
        return MW_MYSQLDUMP;
    }

    public void setMW_MYSQLDUMP(String MW_MYSQLDUMP) {
        this.MW_MYSQLDUMP = MW_MYSQLDUMP;
    }

    public String getMW_MYSQLDUMP_VALUE() {
        return MW_MYSQLDUMP_VALUE;
    }

    public void setMW_MYSQLDUMP_VALUE(String MW_MYSQLDUMP_VALUE) {
        this.MW_MYSQLDUMP_VALUE = MW_MYSQLDUMP_VALUE;
    }

    public String getBACKUPINFO() {
        return BACKUPINFO;
    }

    public void setBACKUPINFO(String BACKUPINFO) {
        this.BACKUPINFO = BACKUPINFO;
    }

    public String getBACKUPINFO_VALUE() {
        return BACKUPINFO_VALUE;
    }

    public void setBACKUPINFO_VALUE(String BACKUPINFO_VALUE) {
        this.BACKUPINFO_VALUE = BACKUPINFO_VALUE;
    }

    public String getBACKUP_MYSQLDUMP() {
        return BACKUP_MYSQLDUMP;
    }

    public void setBACKUP_MYSQLDUMP(String BACKUP_MYSQLDUMP) {
        this.BACKUP_MYSQLDUMP = BACKUP_MYSQLDUMP;
    }

    public String getBACKUP_MYSQLDUMP_VALUE() {
        return BACKUP_MYSQLDUMP_VALUE;
    }

    public void setBACKUP_MYSQLDUMP_VALUE(String BACKUP_MYSQLDUMP_VALUE) {
        this.BACKUP_MYSQLDUMP_VALUE = BACKUP_MYSQLDUMP_VALUE;
    }

    public String getBACKUP_FILECOUNT() {
        return BACKUP_FILECOUNT;
    }

    public void setBACKUP_FILECOUNT(String BACKUP_FILECOUNT) {
        this.BACKUP_FILECOUNT = BACKUP_FILECOUNT;
    }

    public int getBACKUP_FILECOUNT_VALUE() {
        return BACKUP_FILECOUNT_VALUE;
    }

    public void setBACKUP_FILECOUNT_VALUE(int BACKUP_FILECOUNT_VALUE) {
        this.BACKUP_FILECOUNT_VALUE = BACKUP_FILECOUNT_VALUE;
    }

    public String getMW_TEMP_PATH() {
        return MW_TEMP_PATH;
    }

    public void setMW_TEMP_PATH(String MW_TEMP_PATH) {
        this.MW_TEMP_PATH = MW_TEMP_PATH;
    }

    public String getKafkadebuginfo() {
        return kafkadebuginfo;
    }

    public void setKafkadebuginfo(String kafkadebuginfo) {
        this.kafkadebuginfo = kafkadebuginfo;
    }

    public String getSystemdebuginfo() {
        return systemdebuginfo;
    }

    public void setSystemdebuginfo(String systemdebuginfo) {
        this.systemdebuginfo = systemdebuginfo;
    }

    public String getMysqldebuginfo() {
        return mysqldebuginfo;
    }

    public void setMysqldebuginfo(String mysqldebuginfo) {
        this.mysqldebuginfo = mysqldebuginfo;
    }

    public int getMW_DEBUGINFO_TASKTIMEOUT() {
        return MW_DEBUGINFO_TASKTIMEOUT;
    }

    public void setMW_DEBUGINFO_TASKTIMEOUT(int MW_DEBUGINFO_TASKTIMEOUT) {
        this.MW_DEBUGINFO_TASKTIMEOUT = MW_DEBUGINFO_TASKTIMEOUT;
    }

    public String getLogcopydebuginfo() {
        return logcopydebuginfo;
    }

    public void setLogcopydebuginfo(String logcopydebuginfo) {
        this.logcopydebuginfo = logcopydebuginfo;
    }

    public int getDPI_DEBUGINFO_RESPONSETIMEOUT() {
        return DPI_DEBUGINFO_RESPONSETIMEOUT;
    }

    public void setDPI_DEBUGINFO_RESPONSETIMEOUT(int DPI_DEBUGINFO_RESPONSETIMEOUT) {
        this.DPI_DEBUGINFO_RESPONSETIMEOUT = DPI_DEBUGINFO_RESPONSETIMEOUT;
    }

    public int getDPI_DEBUGINFO_TASKTIMEOUT() {
        return DPI_DEBUGINFO_TASKTIMEOUT;
    }

    public void setDPI_DEBUGINFO_TASKTIMEOUT(int DPI_DEBUGINFO_TASKTIMEOUT) {
        this.DPI_DEBUGINFO_TASKTIMEOUT = DPI_DEBUGINFO_TASKTIMEOUT;
    }

    public String getDPI_DEBUGINFO_ISCOREDUMP() {
        return DPI_DEBUGINFO_ISCOREDUMP;
    }

    public void setDPI_DEBUGINFO_ISCOREDUMP(String DPI_DEBUGINFO_ISCOREDUMP) {
        this.DPI_DEBUGINFO_ISCOREDUMP = DPI_DEBUGINFO_ISCOREDUMP;
    }

    public String getDPI_DEBUGINFO_ISCOREDUMP_VALUE() {
        return DPI_DEBUGINFO_ISCOREDUMP_VALUE;
    }

    public void setDPI_DEBUGINFO_ISCOREDUMP_VALUE(String DPI_DEBUGINFO_ISCOREDUMP_VALUE) {
        this.DPI_DEBUGINFO_ISCOREDUMP_VALUE = DPI_DEBUGINFO_ISCOREDUMP_VALUE;
    }

    public int getDPI_ROUTERSETTING_RESPONSETIMEOUT() {
        return DPI_ROUTERSETTING_RESPONSETIMEOUT;
    }

    public void setDPI_ROUTERSETTING_RESPONSETIMEOUT(int DPI_ROUTERSETTING_RESPONSETIMEOUT) {
        this.DPI_ROUTERSETTING_RESPONSETIMEOUT = DPI_ROUTERSETTING_RESPONSETIMEOUT;
    }

    public int getDPI_ROUTERINFO_RESPONSETIMEOUT() {
        return DPI_ROUTERINFO_RESPONSETIMEOUT;
    }

    public void setDPI_ROUTERINFO_RESPONSETIMEOUT(int DPI_ROUTERINFO_RESPONSETIMEOUT) {
        this.DPI_ROUTERINFO_RESPONSETIMEOUT = DPI_ROUTERINFO_RESPONSETIMEOUT;
    }

    public String getUSERPERMISSION() {
        return USERPERMISSION;
    }

    public void setUSERPERMISSION(String USERPERMISSION) {
        this.USERPERMISSION = USERPERMISSION;
    }

    public String getGROUPERMISSION() {
        return GROUPERMISSION;
    }

    public void setGROUPERMISSION(String GROUPERMISSION) {
        this.GROUPERMISSION = GROUPERMISSION;
    }

    public String getSWRUNPERMISSION() {
        return SWRUNPERMISSION;
    }

    public void setSWRUNPERMISSION(String SWRUNPERMISSION) {
        this.SWRUNPERMISSION = SWRUNPERMISSION;
    }

    public String getSWRUNGPERMISSION() {
        return SWRUNGPERMISSION;
    }

    public void setSWRUNGPERMISSION(String SWRUNGPERMISSION) {
        this.SWRUNGPERMISSION = SWRUNGPERMISSION;
    }

    public String getMysqlbackupinfo() {
        return mysqlbackupinfo;
    }

    public void setMysqlbackupinfo(String mysqlbackupinfo) {
        this.mysqlbackupinfo = mysqlbackupinfo;
    }

    public String getSystemdbackupinfo() {
        return systemdbackupinfo;
    }

    public void setSystemdbackupinfo(String systemdbackupinfo) {
        this.systemdbackupinfo = systemdbackupinfo;
    }

    public String getIPRULES() {
        return IPRULES;
    }

    public void setIPRULES(String IPRULES) {
        this.IPRULES = IPRULES;
    }

    public String getIP_RULE() {
        return IP_RULE;
    }

    public void setIP_RULE(String IP_RULE) {
        this.IP_RULE = IP_RULE;
    }

    public String[] getMysqlsystable() {
        return mysqlsystable;
    }

    public void setMysqlsystable(String[] mysqlsystable) {
        this.mysqlsystable = mysqlsystable;
    }

    public String[] getMysqlbustable() {
        return mysqlbustable;
    }

    public void setMysqlbustable(String[] mysqlbustable) {
        this.mysqlbustable = mysqlbustable;
    }

    public String getLasthour() {
        return lasthour;
    }

    public void setLasthour(String lasthour) {
        this.lasthour = lasthour;
    }

    public String getLastday() {
        return lastday;
    }

    public void setLastday(String lastday) {
        this.lastday = lastday;
    }

    public String getLastweek() {
        return lastweek;
    }

    public void setLastweek(String lastweek) {
        this.lastweek = lastweek;
    }
}
