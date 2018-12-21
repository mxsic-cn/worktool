package cn.mysic.json;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by liuchuan on 12/9/16.
 */
@Data
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

}
