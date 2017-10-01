package cn.mysic.json;

import adl.StringHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liuchuan on 10/20/16.
 */
public class ProtocolStructure {

    private static final Gson gson = new Gson();

    public static FormatNode createRoot() {
        return createRoot(false);
    }

    public static FormatNode createRoot(boolean sortByDisplayName) {

        FormatNode root = gson.fromJson(input, new TypeToken<FormatNode>() {
        }.getType());
        List<String> protocolList = AIConfig.getProtocolFileName();
        for (String oneProtocol : protocolList) {
            try {
                FormatNode tmpRoot = ProtocolStructureHelper.Data2Tree(oneProtocol);
                root.getChildren().addAll(tmpRoot.getChildren());
            } catch (Exception e) {
               System.out.print("Create root error: {}" + oneProtocol);
            }
        }

        if(sortByDisplayName){
            root.getChildren().sort((c1, c2) -> c1.getDisplayName().compareToIgnoreCase(c2.getDisplayName()));
        }

        return root;
    }

    public static FormatNode createRoot(Set<String> addProtocol) {
        FormatNode root = gson.fromJson(input, new TypeToken<FormatNode>() {
        }.getType());
        List<String> protocolList = AIConfig.getProtocolFileName();
        for (String oneProtocol : protocolList) {
            try {
                FormatNode tmpRoot = ProtocolStructureHelper.Data2Tree(oneProtocol);
                if (addProtocol.contains(tmpRoot.getChildren().get(0).displayName)) {
                    root.getChildren().addAll(tmpRoot.getChildren());
                    System.out.println(tmpRoot.getChildren().get(0).getName());
                }
            } catch (Exception e) {
                System.out.println("Protocol Structure Error! " + oneProtocol);
            }
        }
        System.out.println("Number of children: " + root.getChildren().size());
        return root;
    }

    public static FormatNode createRoot(Map<String, String> addProtocol, boolean withL2, Map<String, String> openPortsConfiguration) {
        FormatNode root = gson.fromJson(input, new TypeToken<FormatNode>() {
        }.getType());
        List<String> protocolList = AIConfig.getProtocolFileName();
        List<String> knownProtocol = new ArrayList<>();
        for (String oneProtocol : protocolList) {
            try {
                FormatNode tmpRoot = ProtocolStructureHelper.Data2Tree(oneProtocol);
                String protocolName = tmpRoot.getChildren().get(0).displayName;
                // opcua is very different one, it is disgusting
                if (protocolName.equalsIgnoreCase("opcua_tcp")) {
                    protocolName = "OPCUA";
                }
                // add the open ports for this protocol which is custom defined in settings
                if (openPortsConfiguration != null && !openPortsConfiguration.isEmpty()
                        && openPortsConfiguration.keySet().contains(protocolName)) {
                    String openPorts = tmpRoot.getChildren().get(0).getOpenPorts();
                    String configuration = openPortsConfiguration.get(protocolName);
                    if (!StringHelper.checkEmptyString(configuration)) {
                        Set<String> ports = new HashSet<>(Arrays.asList(configuration.split(",")));
                        // get the trim one
                        ports.forEach(port -> port = port.trim());
                        // add the default one
                        ports.add(openPorts);

                        // now we get the right open ports for this protocol
                        tmpRoot.getChildren().get(0).setOpenPorts(ports.stream().filter(port -> !StringHelper.checkEmptyString(port)).collect(Collectors.joining(",")));
                    }
                }
                knownProtocol.add(tmpRoot.getChildren().get(0).displayName.toUpperCase());
//                System.out.println(tmpRoot.getChildren().get(0).displayName);
                if (addProtocol.keySet().contains(tmpRoot.getChildren().get(0).displayName.toUpperCase())) {
//                    System.out.println(addProtocol.get(tmpRoot.getChildren().get(0).displayName));
                    if (withL2) {
                        if (!StringHelper.checkEmpty(addProtocol.get(tmpRoot.getChildren().get(0).displayName.toUpperCase()))) {
                            tmpRoot.getChildren().get(0).openPorts = addProtocol.get(tmpRoot.getChildren().get(0).displayName);
                        }
                        root.getChildren().addAll(tmpRoot.getChildren());
                    } else {
                        if (!tmpRoot.getChildren().get(0).getIsL2()) {
                            if (!StringHelper.checkEmpty(addProtocol.get(tmpRoot.getChildren().get(0).displayName.toUpperCase()))) {
                                tmpRoot.getChildren().get(0).openPorts = addProtocol.get(tmpRoot.getChildren().get(0).displayName);
                            }
                            root.getChildren().addAll(tmpRoot.getChildren());
                        }
                    }
//                    System.out.println(tmpRoot.getChildren().get(0).getName());
                }
            } catch (Exception e) {
                System.out.println("Protocol Structure Error! " + oneProtocol);
            }
        }
        for (String newProtocol : addProtocol.keySet()) {
            if (!knownProtocol.contains(newProtocol)) {
                FormatNode oneProtocol = new FormatNode();
                oneProtocol.type = NodeType.VALUE;
                oneProtocol.openPorts = addProtocol.get(newProtocol);
                oneProtocol.displayName = newProtocol;
                if (!StringHelper.checkEmpty(oneProtocol.openPorts)) {
                    oneProtocol.name = newProtocol + "___" + oneProtocol.openPorts + "___ip";
                    root.getChildren().add(oneProtocol);
                }
            }
        }
//        System.out.println("Number of children: " + root.getChildren().size());
        return root;
    }
    private static final Map<LibraryMap.keyword, String> myLib = LibraryMap.myLib;
    private static final String input = "{name:" +
            myLib.get(LibraryMap.keyword.protocol) + ",displayName:协议,type:KEY,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.ip) + ",displayName:" +
            "所有协议" + ",type:VALUE,active:false,range:\"NA\",children:[]},{name:" +
            myLib.get(LibraryMap.keyword.all) + ",displayName:" +
            myLib.get(LibraryMap.keyword.all) + ",type:VALUE,active:false,range:\"NA\",children:[]},{name:" +
            myLib.get(LibraryMap.keyword.tcp) + ",displayName:" +
            myLib.get(LibraryMap.keyword.tcp) + ",type:VALUE,active:false,range:\"NA\",children:[]},{name:" +
            myLib.get(LibraryMap.keyword.udp) + ",displayName:" +
            myLib.get(LibraryMap.keyword.udp) + ",type:VALUE,active:false,range:\"NA\",children:[]},{name:" +
            myLib.get(LibraryMap.keyword.profinetio) + ",displayName:" +
            myLib.get(LibraryMap.keyword.profinetio) + ",openPorts:\"34962,34963,34964\",type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:功能码,type:KEY,active:false,range:\"NA\",children:[{name:1,displayName:PNIO_OPNUM_RELEASE,type:VALUE,active:false,range:\"NA\",children:[]},{name:0,displayName:PNIO_OPNUM_CONNECT,type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:PNIO_OPNUM_READ_IMPLICIT,type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:PNIO_OPNUM_CONTROL,type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:PNIO_OPNUM_WRITE,type:VALUE,active:false,range:\"NA\",children:[]},{name:2,displayName:PNIO_OPNUM_READ,type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]},{name:" +
            myLib.get(LibraryMap.keyword.uuid) + ",displayName:操作接口,type:KEY,active:false,range:\"NA\",children:[{name:dea000016c9711d1827100a02442df7d,displayName:PNIODevice,type:VALUE,active:false,range:\"NA\",children:[]},{name:dea000026c9711d1827100a02442df7d,displayName:PNIOcontroller,type:VALUE,active:false,range:\"NA\",children:[]},{name:dea000036c9711d1827100a02442df7d,displayName:PNIOSupervisor,type:VALUE,active:false,range:\"NA\",children:[]},{name:dea000046c9711d1827100a02442df7d,displayName:PNIOParameterServer,type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]},{name:blocktype,displayName:数据类型,type:KEY,active:false,range:\"NA\",children:[{name:0x8111,displayName:IODControlRes_Prm_End_Plug_Alarm,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0112,displayName:IOXBlockReq_Application_Ready_Connection,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8112,displayName:IOXBlockRes_Application_Ready_Connection,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0113,displayName:IOXBlockReq_Application_Ready_Plug_Alarm,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0111,displayName:IODControlReq_Prm_End_Plug_Alarm,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8110,displayName:IODControlRes_Prm_End_Connection,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0110,displayName:IODControlReq_Prm_End_Connection,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0109,displayName:IRInfoBlock,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8108,displayName:ARVendorBlockRes,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x010B,displayName:ARFSUBlock,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x010A,displayName:SRInfoBlock,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0107,displayName:SubFrameBlock,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0108,displayName:ARVendorBlockReq,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0106,displayName:MCRBlockReq,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8106,displayName:ARServerBlock,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8105,displayName:PrmServerBlockRes,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0105,displayName:PrmServerBlockReq,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8104,displayName:ModuleDiffBlock,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0104,displayName:ExpectedSubmoduleBlockReq,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8103,displayName:AlarmCRBlockRes,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0103,displayName:AlarmCRBlockReq,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8102,displayName:IOCRBlockRes,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0101,displayName:ARBlockReq,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8101,displayName:ARBlockRes,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0102,displayName:IOCRBlockReq,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x002D,displayName:IM13,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x002C,displayName:IM12,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x002F,displayName:IM15,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x002E,displayName:IM14,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0031,displayName:IM0FilterDataModul,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0030,displayName:IM0FilterDataSubmodul,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8002,displayName:Alarm_Ack_Low,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0032,displayName:IM0FilterDataDevice,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x002A,displayName:IM10,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x002B,displayName:IM11,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0024,displayName:IM4,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0023,displayName:IM3,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0022,displayName:IM2,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0021,displayName:IM1,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0028,displayName:IM8,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0027,displayName:IM7,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0026,displayName:IM6,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0025,displayName:IM5,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0029,displayName:IM9,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0020,displayName:IM0,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x001A,displayName:APIData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x001B,displayName:SRLData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0016,displayName:RecordOutputDataObjectElement,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0017,displayName:DataReserved,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0018,displayName:ARData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0019,displayName:LogData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0012,displayName:ExpectedIdentificationData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0013,displayName:RealIdentificationData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0014,displayName:SubstituteValue,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0015,displayName:RecordInputDataObjectElement,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0011,displayName:IdentificationReserved,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8009,displayName:IODReadResHeader,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0010,displayName:DiagnosisData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8008,displayName:IODWriteResHeader,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0009,displayName:IODReadReqHeader,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0002,displayName:Alarm_Notification_Low,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0008,displayName:IODWriteReqHeader,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0000,displayName:Reserved,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0001,displayName:Alarm_Notification_High,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8001,displayName:Alarm_Ack_High,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0216,displayName:Media_redundancy_manager_parameters,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0217,displayName:Media_redundancy_client_parameters,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x020A,displayName:CheckPeers,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x020B,displayName:CheckLineDelay,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x020C,displayName:Checking_MAUType,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0213,displayName:PDInterfaceMrpDataCheck,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0212,displayName:PDInterfaceMrpDataReal,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0215,displayName:PDPortMrpDataReal,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0214,displayName:PDPortMrpDataAdjust,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x020F,displayName:PDPortDataReal,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x020E,displayName:Adjusting_MAUType,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0211,displayName:PDInterfaceMrpDataAdjust,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0210,displayName:AdjustMulticastBoundary,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0201,displayName:PDevData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0202,displayName:PDPortDataAdjust,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8118,displayName:ControlBlockPrmBeginRes,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0200,displayName:PDPortDataCheck,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0205,displayName:PDIRData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0204,displayName:IsochronousModeData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0203,displayName:PDSyncData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0209,displayName:AdjustDomainBoundary,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0208,displayName:PDIRBeginEndData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0207,displayName:PDIRFrameData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0206,displayName:PDIRGlobalData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8113,displayName:IOXBlockRes_Application_Ready_Plug_Alarm,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0114,displayName:IODReleaseReq,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8114,displayName:IODReleaseRes,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0115,displayName:ARRPCServerBlockReq,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8115,displayName:ARRPCServerBlockRes,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8116,displayName:IOXControlRes_Ready_for_Companion,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0116,displayName:IOXControlReq_Ready_for_Companion,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x8117,displayName:IOXControlRes_Ready_for_RT_CLASS_3,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0117,displayName:IOXControlReq_Ready_for_RT_CLASS_3,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0119,displayName:SubmoduleListBlock,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0118,displayName:ControlBlockPrmBegin,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0400,displayName:MultipleBlockHeader,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0251,displayName:PDPortStatistic,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0600,displayName:FSHello,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0500,displayName:RecordDataReadQuery,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0233,displayName:MrpInstanceDataCheck,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0232,displayName:MrpInstanceDataReal,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0250,displayName:PDInterfaceAdjust,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0240,displayName:PDInterfaceDataReal,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0608,displayName:PDInterfaceFSUDataAdjust,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0601,displayName:FSParameterBlock,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0609,displayName:ARFSUDataAdjust,type:VALUE,active:false,range:\"NA\",children:[]},{name:0xB070,displayName:OHA_Info_(OHA),type:VALUE,active:false,range:\"NA\",children:[]},{name:0xB061,displayName:EDD_Trace_Unit2_(EDD),type:VALUE,active:false,range:\"NA\",children:[]},{name:0xB060,displayName:EDD_Trace_Unit1_(EDD),type:VALUE,active:false,range:\"NA\",children:[]},{name:0xB051,displayName:ExtPLL_Control_RTA_SyncID_1_(GSY),type:VALUE,active:false,range:\"NA\",children:[]},{name:0xB050,displayName:ExtPLL_Control_RTC_RTA_SyncID_0_(EDD),type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0702,displayName:AutoConfiguration_Configuration,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0701,displayName:AutoConfiguration_Communication,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0700,displayName:AutoConfiguration,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0F00,displayName:MaintenanceItem,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0F01,displayName:Upload_selected_Records_within_Upload_RetrievalItem,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0F02,displayName:iParameterItem,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0219,displayName:Media_redundancy_ring_state_data,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0218,displayName:Media_redundancy_RT_mode_for_manager,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x021B,displayName:Adjust_LinkState,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x021A,displayName:Media_redundancy_RT_ring_state_data,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x021D,displayName:Media_redundancy_RT_mode_for_clients,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x021C,displayName:Checking_LinkState,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x021F,displayName:CheckMAUTypeDifference,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x021E,displayName:CheckSyncDifference,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0221,displayName:Reading_real_fiber_optic_manufacturerspecific_data,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0220,displayName:PDPortFODataReal,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0224,displayName:Adjust_PeerToPeerBoundary,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0223,displayName:PDPortFODataCheck,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0222,displayName:PDPortFODataAdjust,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x022A,displayName:PDIRSubframeData,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0227,displayName:Adjust_FastForwardingBoundary,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0226,displayName:Adjust_PreambleLength,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0225,displayName:Adjust_DCPBoundary,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0231,displayName:MrpInstanceDataAdjust,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0230,displayName:PDNCDataCheck,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x022B,displayName:SubframeBlock,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0F03,displayName:Retrieve_selected_Records_within_Upload_RetrievalItem,type:VALUE,active:false,range:\"NA\",children:[]},{name:0x0F04,displayName:Retrieve_all_Records_within_Upload_RetrievalItem,type:VALUE,active:false,range:\"NA\",children:[]},{name:0,displayName:NA,type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:" +
            myLib.get(LibraryMap.keyword.modbus) + ",displayName:" +
            myLib.get(LibraryMap.keyword.modbus) + ",openPorts:\"502\",type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.func) + ",displayName:功能码,type:KEY,active:false,range:\"NA\",children:[{name:5,displayName:Write_Single_Coil,type:VALUE,active:false,range:\"NA\",children:[{name:startaddr,displayName:起始地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]}]},{name:6,displayName:Write_Single_Register,type:VALUE,active:false,range:\"NA\",children:[{name:startaddr,displayName:起始地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]}]},{name:24,displayName:Read_FIFO_Queue,type:VALUE,active:false,range:\"NA\",children:[{name:fifoaddr,displayName:fifo地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]}]},{name:8,displayName:Diagnostic,type:VALUE,active:false,range:\"NA\",children:[{name:subfunc,displayName:subfunc,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,4;10,18;20]\",children:[]}]}]},{name:16,displayName:Write_Multiple_Registers,type:VALUE,active:false,range:\"NA\",children:[{name:startaddr,displayName:起始地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]},{name:endaddr,displayName:终止地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[1,2000]\",children:[]}]}]},{name:21,displayName:Write_File_Record,type:VALUE,active:false,range:\"NA\",children:[]},{name:15,displayName:Write_Multiple_Coils,type:VALUE,active:false,range:\"NA\",children:[{name:startaddr,displayName:起始地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]},{name:endaddr,displayName:终止地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[1,2000]\",children:[]}]}]},{name:1,displayName:Read_Coils,type:VALUE,active:false,range:\"NA\",children:[{name:startaddr,displayName:起始地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]},{name:endaddr,displayName:终止地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[1,2000]\",children:[]}]}]},{name:22,displayName:Mask_Write_Register,type:VALUE,active:false,range:\"NA\",children:[{name:refaddr,displayName:refaddr,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]}]},{name:7,displayName:Read_Exception_Status,type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Read_Input_Register,type:VALUE,active:false,range:\"NA\",children:[{name:startaddr,displayName:起始地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]},{name:endaddr,displayName:终止地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[1,2000]\",children:[]}]}]},{name:12,displayName:Get_Com_Event_Log,type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:Read_Holding_Registers,type:VALUE,active:false,range:\"NA\",children:[{name:startaddr,displayName:起始地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]},{name:endaddr,displayName:终止地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[1,2000]\",children:[]}]}]},{name:43,displayName:Read_Device_Identification,type:VALUE,active:false,range:\"NA\",children:[{name:meitype,displayName:meitype,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[13,14]\",children:[]}]}]},{name:11,displayName:Get_Com_Event_Counter,type:VALUE,active:false,range:\"NA\",children:[]},{name:23,displayName:Read_Write_Multiple_Registers,type:VALUE,active:false,range:\"NA\",children:[{name:rdstartaddr,displayName:起始读地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]},{name:rdendaddr,displayName:终止读地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[1,2000]\",children:[]}]},{name:wtstartaddr,displayName:起始写地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]},{name:wtendaddr,displayName:终止写地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[1,121]\",children:[]}]}]},{name:2,displayName:Read_Discrete_Inputs,type:VALUE,active:false,range:\"NA\",children:[{name:startaddr,displayName:起始地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[0,65535]\",children:[]}]},{name:endaddr,displayName:终止地址,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"[1,2000]\",children:[]}]}]},{name:17,displayName:Report_Slave_ID,type:VALUE,active:false,range:\"NA\",children:[]},{name:20,displayName:Read_File_Record,type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:" +
            myLib.get(LibraryMap.keyword.unknownNode) + ",displayName:" +
            myLib.get(LibraryMap.keyword.unknownNode) + ",type:VALUE,active:false,range:\"NA\",children:[]},{name:" +
            myLib.get(LibraryMap.keyword.support) + ",displayName:" +
            myLib.get(LibraryMap.keyword.support) + ",type:VALUE,active:false,range:\"NA\",children:[]},{name:" +
            myLib.get(LibraryMap.keyword.opcda) + ",displayName:" +
            myLib.get(LibraryMap.keyword.opcda) + ",openPorts:\"135,137,138,139\",type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.uuid) + ",displayName:操作接口,type:KEY,active:false,range:\"NA\",children:[{name:63d5f430cfe411d1b2c80060083ba1fb,displayName:CATID_OPCDAServer10,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a4e011e11d096750020afd8adb3,displayName:IOPCServerPublicGroups,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:GetPublicGroupByName(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:RemovePublicGroup(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39227004a18f4b578b0a5235670f4468,displayName:IOPCBrowse,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:Browse(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:GetProperties(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a4f011e11d096750020afd8adb3,displayName:IOPCBrowseServerAddressSpace,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:QueryOrganization(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:BrowseAccessPaths(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:GetItemID(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:BrowseOPCItemIDs(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:ChangeBrowsePosition(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:85c0b42728934cbcbd78e5fc5146f08f,displayName:IOPCItemIO,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:WriteVQT(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:Read(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a70011e11d096750020afd8adb3,displayName:IOPCDataCallback,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:6,displayName:OnCancelComplete(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:OnWriteComplete(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:OnDataChange(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:OnReadComplete(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a50011e11d096750020afd8adb3,displayName:IOPCGroupStateMgt,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:GetState(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:CloneGroup(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:SetName(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:SetState(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:65168857578311d184a000608cb8a7e9,displayName:IOPCEventAreaBrowser,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:6,displayName:GetQualifiedSourceName(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:GetQualifiedAreaName(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:BrowseOPCAreas(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:ChangeBrowsePosition(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:a0c85bb841614fd68655bb584601c9e0,displayName:CATID_OPCDXServer10,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a52011e11d096750020afd8adb3,displayName:IOPCSyncIO,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:Read(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Write(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:5946da938b394ec8ab3daa73df5bc86f,displayName:IOPCItemDeadbandMgt,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:GetItemDeadband(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:SetItemDeadband(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:ClearItemDeadband(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a72011e11d096750020afd8adb3,displayName:IOPCItemProperties,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:QueryAvailableProperties(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:LookupItemIDs(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:GetItemProperties(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a51011e11d096750020afd8adb3,displayName:IOPCPublicGroupStateMgt,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:GetState(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:MoveToPublic(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a54011e11d096750020afd8adb3,displayName:IOPCItemMgt,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:9,displayName:CreateEnumerator(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:SetDatatypes(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:ValidateItems(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:SetActiveState(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:SetClientHandles(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:AddItems(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:RemoveItems(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:65168851578311d184a000608cb8a7e9,displayName:IOPCEventServer,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:10,displayName:QueryEventAttributes(10),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:QuerySubConditionNames(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:14,displayName:EnableConditionBySource(14),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:GetStatus(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:15,displayName:DisableConditionByArea(15),type:VALUE,active:false,range:\"NA\",children:[]},{name:11,displayName:TranslateToItemIDs(11),type:VALUE,active:false,range:\"NA\",children:[]},{name:18,displayName:CreateAreaBrowser(18),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:QueryEventCategories(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:13,displayName:EnableConditionByArea(13),type:VALUE,active:false,range:\"NA\",children:[]},{name:9,displayName:QuerySourceConditions(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:17,displayName:AckCondition(17),type:VALUE,active:false,range:\"NA\",children:[]},{name:16,displayName:DisableConditionBySource(16),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:QueryAvailableFilters(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:CreateEventSubscription(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:QueryConditionNames(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:12,displayName:GetConditionState(12),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:1f1217b0dee011d2a5e5000086339399,displayName:IOPCHDA_Server,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:7,displayName:ReleaseItemHandles(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:GetItemHandles(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:9,displayName:CreateBrowse(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:GetHistorianStatus(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:GetItemAttributes(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:ValidateItemIDs(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:GetAggregates(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:94c955dc36844ccbafabf898ce19aac3,displayName:IOPCEventSubscriptionMgt2,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:GetKeepAlive(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:SetKeepAlive(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:1f1217b7dee011d2a5e5000086339399,displayName:IOPCHDA_AsyncAnnotations,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:5,displayName:Insert(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:QueryCapabilities(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:Cancel(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Read(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:cc60364266d748f1b69ab625e73652d7,displayName:CATID_OPCDAServer30,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a55011e11d096750020afd8adb3,displayName:IEnumOPCItemAttributes,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:Skip(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:Reset(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:Clone(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:Next(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:65168844578311d184a000608cb8a7e9,displayName:LIBID_OPC_AE,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:3ca18b30108847d589520b12b027ed32,displayName:LIBID_OPC_DX,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:1f1217b3dee011d2a5e5000086339399,displayName:IOPCHDA_SyncUpdate,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:QueryCapabilities(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Insert(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:DeleteAtTime(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:Replace(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:DeleteRaw(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:InsertReplace(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:3b540b5103784551adccea9b104302bf,displayName:LIBID_OPC_DA,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:3098eda4a00648b2a27f247453959408,displayName:CATID_XMLDAServer10,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a53011e11d096750020afd8adb3,displayName:IOPCAsyncIO,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:5,displayName:Refresh(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:Read(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:Cancel(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Write(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:0000000000000000c000000000000046,displayName:Iunknown,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:0,displayName:QueryInterface(0),type:VALUE,active:false,range:\"NA\",children:[]},{name:2,displayName:Release(2),type:VALUE,active:false,range:\"NA\",children:[]},{name:1,displayName:AddRef(1),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:1f1217b5dee011d2a5e5000086339399,displayName:IOPCHDA_AsyncRead,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:8,displayName:ReadModified(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:ReadRaw(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:ReadProcessed(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:10,displayName:Cancel(10),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:AdviseProcessed(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:AdviseRaw(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:9,displayName:ReadAttribute(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:ReadAtTime(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:730f5f0f55b14c819e18ff8a0904e1fa,displayName:IOPCSyncIO2,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:6,displayName:WriteVQT(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:ReadMaxAge(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:Read(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Write(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:1f1217b4dee011d2a5e5000086339399,displayName:IOPCHDA_SyncAnnotations,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:Read(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:QueryCapabilities(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:Insert(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:3e22d313f08b41a586c895e95cb49ffc,displayName:IOPCItemSamplingMgt,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:5,displayName:ClearItemSamplingRate(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:SetItemSamplingRate(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:GetItemSamplingRate(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:SetItemBufferEnable(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:GetItemBufferEnable(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:6516885f578311d184a000608cb8a7e9,displayName:IOPCEventSink,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:OnEvent(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:1f1217b6dee011d2a5e5000086339399,displayName:IOPCHDA_AsyncUpdate,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:9,displayName:Cancel(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Insert(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:InsertReplace(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:QueryCapabilities(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:Replace(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:DeleteAtTime(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:DeleteRaw(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:1f1217b9dee011d2a5e5000086339399,displayName:IOPCHDA_DataCallback,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:6,displayName:OnReadAttributeComplete(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:9,displayName:OnPlayback(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:OnReadComplete(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:OnReadModifiedComplete(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:OnDataChange(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:10,displayName:OnUpdateComplete(10),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:OnReadAnnotations(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:11,displayName:OnCancelComplete(11),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:OnInsertAnnotations(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:58e13251ac8711d184d500608cb8a7e9,displayName:OPCEventServerCATID,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:65168855578311d184a000608cb8a7e9,displayName:IOPCEventSubscriptionMgt,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:10,displayName:SetState(10),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:SelectReturnedAttributes(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:GetFilter(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:9,displayName:GetState(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:Refresh(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:CancelRefresh(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:SetFilter(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:GetReturnedAttributes(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:63d5f432cfe411d1b2c80060083ba1fb,displayName:CATID_OPCDAServer20,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:f31dfde207b611d2b2d80060083ba1fb,displayName:IOPCCommon,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:5,displayName:QueryAvailableLocaleIDs(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:GetErrorString(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:SetLocaleID(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:GetLocaleID(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:SetClientName(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:1f1217b1dee011d2a5e5000086339399,displayName:IOPCHDA_Browser,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:ChangeBrowsePosition(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:GetEnum(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:GetBranchPosition(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:GetItemID(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:1f1217b2dee011d2a5e5000086339399,displayName:IOPCHDA_SyncRead,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:ReadRaw(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:ReadProcessed(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:ReadAtTime(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:ReadAttribute(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:ReadModified(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:71bbe88e95644bcdbcfc71c558d94f2d,displayName:IOPCEventServer2,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:EnableConditionBySource2(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:DisableConditionBySource2(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:GetEnableStateByArea(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:DisableConditionByArea2(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:EnableConditionByArea2(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:GetEnableStateBySource(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:f31dfde107b611d2b2d80060083ba1fb,displayName:IOPCShutdown,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:ShutdownRequest(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:b196b286bab4101ab69c00aa00341d07,displayName:IConnectionPoint,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:GetConnectionPointContainer(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:Advise(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:Unadvise(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:EnumConnections(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:GetConnectionInterface(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:0967b97b36ef423eb6f86bff1e40d39d,displayName:IOPCAsyncIO3,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:10,displayName:WriteVQT(10),type:VALUE,active:false,range:\"NA\",children:[]},{name:9,displayName:ReadMaxAge(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:11,displayName:RefreshMaxAge(11),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:GetEnable(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:Refresh2(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Write(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:SetEnable(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:Cancel2(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:Read(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:9dd0b56cad9e43ee8305487f3188bf7a,displayName:IOPCServerList2,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:GetClassDetails(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:CLSIDFromProgID(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:EnumClassesOfCategories(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:8e368666d72e4f7887ed647611c61c9f,displayName:IOPCGroupStateMgt2,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:7,displayName:SetKeepAlive(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:GetKeepAlive(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:SetName(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:CloneGroup(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:GetState(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:SetState(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:1f1217b8dee011d2a5e5000086339399,displayName:IOPCHDA_Playback,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:5,displayName:Cancel(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:ReadRawWithUpdate(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:ReadProcessedWithUpdate(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a4d011e11d096750020afd8adb3,displayName:IOPCServer,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:AddGroup(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:GetErrorString(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:GetGroupByName(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:GetStatus(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:RemoveGroup(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:CreateGroupEnumerator(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:55c382c821c74e8896c1becfb1e3f483,displayName:IOPCEnumGUID,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:5,displayName:Reset(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:Clone(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Skip(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:Next(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:c130d281f4aa47798846c2c4cb444f2a,displayName:IOPCConfiguration,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:4,displayName:AddServers(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:DeleteServers(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:13,displayName:CopyDXConnectionDefaultAttributes(13),type:VALUE,active:false,range:\"NA\",children:[]},{name:12,displayName:DeleteDXConnections(12),type:VALUE,active:false,range:\"NA\",children:[]},{name:10,displayName:UpdateDXConnections(10),type:VALUE,active:false,range:\"NA\",children:[]},{name:11,displayName:ModifyDXConnections(11),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:ModifyServers(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:CopyDefaultServerAttributes(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:9,displayName:AddDXConnections(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:14,displayName:ResetConfiguration(14),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:GetServers(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:QueryDXConnections(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:13486d50482111d2a4943cb306c10000,displayName:IOPCServerList,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:3,displayName:EnumClassesOfCategories(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:GetClassDetails(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:CLSIDFromProgID(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:39c13a71011e11d096750020afd8adb3,displayName:IOPCAsyncIO2,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:6,displayName:Cancel2(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Write(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:Refresh2(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:SetEnable(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:Read(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:GetEnable(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.opnum) + ",displayName:操作码,type:KEY,active:false,range:\"NA\",children:[{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]}]}]},{name:" +
            myLib.get(LibraryMap.keyword.dnp3) + ",displayName:" +
            myLib.get(LibraryMap.keyword.dnp3) + ",openPorts:\"20000\",type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.func) + ",displayName:功能码,type:KEY,active:false,range:\"NA\",children:[{name:6,displayName:Direct_Operate_No_ACK,type:VALUE,active:false,range:\"NA\",children:[]},{name:20,displayName:Enable_Spontaneous_Msg,type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:Immediate_Freeze_No_ACK,type:VALUE,active:false,range:\"NA\",children:[]},{name:16,displayName:Initialize_Application,type:VALUE,active:false,range:\"NA\",children:[]},{name:21,displayName:Disable_Spontaneous_Msg,type:VALUE,active:false,range:\"NA\",children:[]},{name:31,displayName:Activate_Config,type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:Direct_Operate,type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Operate,type:VALUE,active:false,range:\"NA\",children:[]},{name:29,displayName:Authenticate_File,type:VALUE,active:false,range:\"NA\",children:[]},{name:0,displayName:Confirm,type:VALUE,active:false,range:\"NA\",children:[]},{name:22,displayName:Assign_Classes,type:VALUE,active:false,range:\"NA\",children:[]},{name:10,displayName:Freeze_and_Clear_No_ACK,type:VALUE,active:false,range:\"NA\",children:[]},{name:19,displayName:Save_Configuration,type:VALUE,active:false,range:\"NA\",children:[]},{name:1,displayName:Read,type:VALUE,active:false,range:\"NA\",children:[]},{name:27,displayName:Delete_File,type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:Select,type:VALUE,active:false,range:\"NA\",children:[]},{name:18,displayName:Stop_Application,type:VALUE,active:false,range:\"NA\",children:[]},{name:28,displayName:Get_File_Info,type:VALUE,active:false,range:\"NA\",children:[]},{name:17,displayName:Start_Application,type:VALUE,active:false,range:\"NA\",children:[]},{name:32,displayName:Authentication_Request,type:VALUE,active:false,range:\"NA\",children:[]},{name:33,displayName:Authentication_Error,type:VALUE,active:false,range:\"NA\",children:[]},{name:130,displayName:Unsolicited_Response,type:VALUE,active:false,range:\"NA\",children:[]},{name:12,displayName:Freeze_With_Time_No_ACK,type:VALUE,active:false,range:\"NA\",children:[]},{name:30,displayName:Abort_File,type:VALUE,active:false,range:\"NA\",children:[]},{name:25,displayName:Open_File,type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:Immediate_Freeze,type:VALUE,active:false,range:\"NA\",children:[]},{name:9,displayName:Freeze_and_Clear,type:VALUE,active:false,range:\"NA\",children:[]},{name:23,displayName:Delay_Measurement,type:VALUE,active:false,range:\"NA\",children:[]},{name:11,displayName:Freeze_With_Time,type:VALUE,active:false,range:\"NA\",children:[]},{name:14,displayName:Warm_Restart,type:VALUE,active:false,range:\"NA\",children:[]},{name:26,displayName:Close_File,type:VALUE,active:false,range:\"NA\",children:[]},{name:15,displayName:Initialize_Data,type:VALUE,active:false,range:\"NA\",children:[]},{name:129,displayName:Response,type:VALUE,active:false,range:\"NA\",children:[]},{name:131,displayName:Authentication_Response,type:VALUE,active:false,range:\"NA\",children:[]},{name:13,displayName:Cold_Restart,type:VALUE,active:false,range:\"NA\",children:[]},{name:24,displayName:Record_Current_Time,type:VALUE,active:false,range:\"NA\",children:[]},{name:2,displayName:Write,type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:" +
            myLib.get(LibraryMap.keyword.iec104) + ",displayName:" +
            myLib.get(LibraryMap.keyword.iec104) + ",openPorts:\"2404\",type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.causetx_type) + ",displayName:causetx类型,type:KEY,active:false,range:\"NA\",children:[{name:30,displayName:Inro10(30),type:VALUE,active:false,range:\"NA\",children:[]},{name:45,displayName:UkCauseTx(45),type:VALUE,active:false,range:\"NA\",children:[]},{name:1,displayName:Per_Cyc(1),type:VALUE,active:false,range:\"NA\",children:[]},{name:36,displayName:Inro16(36),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:ActCon(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:28,displayName:Inro8(28),type:VALUE,active:false,range:\"NA\",children:[]},{name:34,displayName:Inro14(34),type:VALUE,active:false,range:\"NA\",children:[]},{name:9,displayName:DeactCon(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:13,displayName:File(13),type:VALUE,active:false,range:\"NA\",children:[]},{name:2,displayName:Back(2),type:VALUE,active:false,range:\"NA\",children:[]},{name:32,displayName:Inro12(32),type:VALUE,active:false,range:\"NA\",children:[]},{name:26,displayName:Inro6(26),type:VALUE,active:false,range:\"NA\",children:[]},{name:29,displayName:Inro9(29),type:VALUE,active:false,range:\"NA\",children:[]},{name:23,displayName:Inro3(23),type:VALUE,active:false,range:\"NA\",children:[]},{name:10,displayName:ActTerm(10),type:VALUE,active:false,range:\"NA\",children:[]},{name:39,displayName:Reqco2(39),type:VALUE,active:false,range:\"NA\",children:[]},{name:40,displayName:Reqco3(40),type:VALUE,active:false,range:\"NA\",children:[]},{name:12,displayName:Retloc(12),type:VALUE,active:false,range:\"NA\",children:[]},{name:27,displayName:Inro7(27),type:VALUE,active:false,range:\"NA\",children:[]},{name:4,displayName:Init(4),type:VALUE,active:false,range:\"NA\",children:[]},{name:47,displayName:UkIOA(47),type:VALUE,active:false,range:\"NA\",children:[]},{name:37,displayName:Reqcogen(37),type:VALUE,active:false,range:\"NA\",children:[]},{name:21,displayName:Inro1(21),type:VALUE,active:false,range:\"NA\",children:[]},{name:24,displayName:Inro4(24),type:VALUE,active:false,range:\"NA\",children:[]},{name:6,displayName:Act(6),type:VALUE,active:false,range:\"NA\",children:[]},{name:41,displayName:Reqco4(41),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:Req(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:20,displayName:Inrogen(20),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:Spont(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:22,displayName:Inro2(22),type:VALUE,active:false,range:\"NA\",children:[]},{name:35,displayName:Inro15(35),type:VALUE,active:false,range:\"NA\",children:[]},{name:8,displayName:Deact(8),type:VALUE,active:false,range:\"NA\",children:[]},{name:11,displayName:Retrem(11),type:VALUE,active:false,range:\"NA\",children:[]},{name:31,displayName:Inro11(31),type:VALUE,active:false,range:\"NA\",children:[]},{name:33,displayName:Inro13(33),type:VALUE,active:false,range:\"NA\",children:[]},{name:46,displayName:UkComAdrASDU(46),type:VALUE,active:false,range:\"NA\",children:[]},{name:25,displayName:Inro5(25),type:VALUE,active:false,range:\"NA\",children:[]},{name:38,displayName:Reqco1(38),type:VALUE,active:false,range:\"NA\",children:[]},{name:44,displayName:UkTypeId(44),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]},{name:" +
            myLib.get(LibraryMap.keyword.asdu_type) + ",displayName:asdu类型,type:KEY,active:false,range:\"NA\",children:[{name:120,displayName:F_FR_NA_1(120),type:VALUE,active:false,range:\"NA\",children:[]},{name:126,displayName:F_DR_TA_1(126),type:VALUE,active:false,range:\"NA\",children:[]},{name:63,displayName:C_SE_TC_1(63),type:VALUE,active:false,range:\"NA\",children:[]},{name:46,displayName:C_DC_NA_1(46),type:VALUE,active:false,range:\"NA\",children:[]},{name:50,displayName:C_SE_NC_1(50),type:VALUE,active:false,range:\"NA\",children:[]},{name:125,displayName:F_SG_NA_1(125),type:VALUE,active:false,range:\"NA\",children:[]},{name:38,displayName:M_EP_TD_1(38),type:VALUE,active:false,range:\"NA\",children:[]},{name:34,displayName:M_ME_TD_1(34),type:VALUE,active:false,range:\"NA\",children:[]},{name:103,displayName:C_CS_NA_1(103),type:VALUE,active:false,range:\"NA\",children:[]},{name:127,displayName:F_SC_NB_1(127),type:VALUE,active:false,range:\"NA\",children:[]},{name:105,displayName:C_RP_NA_1(105),type:VALUE,active:false,range:\"NA\",children:[]},{name:101,displayName:C_CI_NA_1(101),type:VALUE,active:false,range:\"NA\",children:[]},{name:33,displayName:M_BO_TB_1(33),type:VALUE,active:false,range:\"NA\",children:[]},{name:21,displayName:M_ME_ND_1(21),type:VALUE,active:false,range:\"NA\",children:[]},{name:5,displayName:M_ST_NA_1(5),type:VALUE,active:false,range:\"NA\",children:[]},{name:60,displayName:C_RC_TA_1(60),type:VALUE,active:false,range:\"NA\",children:[]},{name:58,displayName:C_SC_TA_1(58),type:VALUE,active:false,range:\"NA\",children:[]},{name:70,displayName:M_EI_NA_1(70),type:VALUE,active:false,range:\"NA\",children:[]},{name:20,displayName:M_PS_NA_1(20),type:VALUE,active:false,range:\"NA\",children:[]},{name:7,displayName:M_BO_NA_1(7),type:VALUE,active:false,range:\"NA\",children:[]},{name:112,displayName:P_ME_NC_1(112),type:VALUE,active:false,range:\"NA\",children:[]},{name:61,displayName:C_SE_TA_1(61),type:VALUE,active:false,range:\"NA\",children:[]},{name:64,displayName:C_BO_TA_1(64),type:VALUE,active:false,range:\"NA\",children:[]},{name:11,displayName:M_ME_NB_1(11),type:VALUE,active:false,range:\"NA\",children:[]},{name:100,displayName:C_IC_NA_1(100),type:VALUE,active:false,range:\"NA\",children:[]},{name:13,displayName:M_ME_NC_1(13),type:VALUE,active:false,range:\"NA\",children:[]},{name:9,displayName:M_ME_NA_1(9),type:VALUE,active:false,range:\"NA\",children:[]},{name:35,displayName:M_ME_TE_1(35),type:VALUE,active:false,range:\"NA\",children:[]},{name:48,displayName:C_SE_NA_1(48),type:VALUE,active:false,range:\"NA\",children:[]},{name:107,displayName:C_TS_TA_1(107),type:VALUE,active:false,range:\"NA\",children:[]},{name:102,displayName:C_RD_NA_1(102),type:VALUE,active:false,range:\"NA\",children:[]},{name:31,displayName:M_DP_TB_1(31),type:VALUE,active:false,range:\"NA\",children:[]},{name:110,displayName:P_ME_NA_1(110),type:VALUE,active:false,range:\"NA\",children:[]},{name:121,displayName:F_SR_NA_1(121),type:VALUE,active:false,range:\"NA\",children:[]},{name:15,displayName:M_IT_NA_1(15),type:VALUE,active:false,range:\"NA\",children:[]},{name:3,displayName:M_DP_NA_1(3),type:VALUE,active:false,range:\"NA\",children:[]},{name:51,displayName:C_BO_NA_1(51),type:VALUE,active:false,range:\"NA\",children:[]},{name:30,displayName:M_SP_TB_1(30),type:VALUE,active:false,range:\"NA\",children:[]},{name:36,displayName:M_ME_TF_1(36),type:VALUE,active:false,range:\"NA\",children:[]},{name:1,displayName:M_SP_NA_1(1),type:VALUE,active:false,range:\"NA\",children:[]},{name:39,displayName:M_EP_TE_1(39),type:VALUE,active:false,range:\"NA\",children:[]},{name:111,displayName:P_ME_NB_1(111),type:VALUE,active:false,range:\"NA\",children:[]},{name:32,displayName:M_ST_TB_1(32),type:VALUE,active:false,range:\"NA\",children:[]},{name:45,displayName:C_SC_NA_1(45),type:VALUE,active:false,range:\"NA\",children:[]},{name:40,displayName:M_EP_TF_1(40),type:VALUE,active:false,range:\"NA\",children:[]},{name:37,displayName:M_IT_TB_1(37),type:VALUE,active:false,range:\"NA\",children:[]},{name:47,displayName:C_RC_NA_1(47),type:VALUE,active:false,range:\"NA\",children:[]},{name:124,displayName:F_AF_NA_1(124),type:VALUE,active:false,range:\"NA\",children:[]},{name:59,displayName:C_DC_TA_1(59),type:VALUE,active:false,range:\"NA\",children:[]},{name:62,displayName:C_SE_TB_1(62),type:VALUE,active:false,range:\"NA\",children:[]},{name:113,displayName:P_AC_NA_1(113),type:VALUE,active:false,range:\"NA\",children:[]},{name:122,displayName:F_SC_NA_1(122),type:VALUE,active:false,range:\"NA\",children:[]},{name:123,displayName:F_LS_NA_1(123),type:VALUE,active:false,range:\"NA\",children:[]},{name:49,displayName:C_SE_NB_1(49),type:VALUE,active:false,range:\"NA\",children:[]},{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}]}]},{name:" +
            myLib.get(LibraryMap.keyword.mms) + ",displayName:" +
            myLib.get(LibraryMap.keyword.mms) + ",openPorts:\"1755\",type:VALUE,active:false,range:\"NA\",children:[{name:" +
            myLib.get(LibraryMap.keyword.pduType) + ",displayName:pdu类型,type:KEY,active:false,range:\"NA\",children:[{" +
            "                            \"name\": \"0\"," +
            "                            \"displayName\": \"Confirmed_Request\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"parentName\": \"\"," +
            "                            \"children\": [" +
            "                                {" +
            "                                    \"name\": " + myLib.get(LibraryMap.keyword.ServiceRequest) + "," +
            "                                    \"displayName\": \"请求类型\"," +
            "                                    \"type\": \"KEY\"," +
            "                                    \"active\": false," +
            "                                    \"range\": \"NA\"," +
            "                                    \"children\": [" +
            "                                        {" +
            "                                            \"name\": \"0\"," +
            "                                            \"displayName\": \"Status_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"1\"," +
            "                                            \"displayName\": \"Get_Name_List_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"2\"," +
            "                                            \"displayName\": \"Identify_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }, " +
            "                                        {" +
            "                                            \"name\": \"3\"," +
            "                                            \"displayName\": \"Rename_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"4\"," +
            "                                            \"displayName\": \"Read_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"5\"," +
            "                                            \"displayName\": \"Write_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"6\"," +
            "                                            \"displayName\": \"Get_Variable_Access_Attributes_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"7\"," +
            "                                            \"displayName\": \"Define_Named_Variable_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"8\"," +
            "                                            \"displayName\": \"Define_Scattered_Access_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"9\"," +
            "                                            \"displayName\": \"Get_Scattered_Access_Attributes_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"10\"," +
            "                                            \"displayName\": \"Delete_Variable_Access_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"11\"," +
            "                                            \"displayName\": \"Define_Named_Variable_List_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"12\"," +
            "                                            \"displayName\": \"Get_Named_Variable_List_Attributes_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"13\"," +
            "                                            \"displayName\": \"Delete_Named_Variable_List_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"14\"," +
            "                                            \"displayName\": \"Define_Named_Type_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"15\"," +
            "                                            \"displayName\": \"Get_Named_Type_Attributes_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"16\"," +
            "                                            \"displayName\": \"Delete_Named_Type_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"17\"," +
            "                                            \"displayName\": \"Input_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"18\"," +
            "                                            \"displayName\": \"Output_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"19\"," +
            "                                            \"displayName\": \"Take_Control_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"20\"," +
            "                                            \"displayName\": \"Relinquish_Control_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"21\"," +
            "                                            \"displayName\": \"Define_Semaphore_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"22\"," +
            "                                            \"displayName\": \"Delete_Semaphore_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"23\"," +
            "                                            \"displayName\": \"Report_Semaphore_Status_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"24\"," +
            "                                            \"displayName\": \"Report_Pool_Semaphore_Status_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"25\"," +
            "                                            \"displayName\": \"Report_Semaphore_Entry_Status_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"26\"," +
            "                                            \"displayName\": \"Initiate_Download_Sequence_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"27\"," +
            "                                            \"displayName\": \"Download_Segment_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"28\"," +
            "                                            \"displayName\": \"Terminate_Download_Sequence_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"29\"," +
            "                                            \"displayName\": \"Initiate_Upload_Sequence_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"30\"," +
            "                                            \"displayName\": \"Upload_Segment_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"31\"," +
            "                                            \"displayName\": \"Terminate_Upload_Sequence_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"32\"," +
            "                                            \"displayName\": \"Request_Domain_Download_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"33\"," +
            "                                            \"displayName\": \"Request_Domain_Upload_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"34\"," +
            "                                            \"displayName\": \"Load_Domain_Content_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"35\"," +
            "                                            \"displayName\": \"Store_Domain_Content_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"36\"," +
            "                                            \"displayName\": \"Delete_Domain_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"37\"," +
            "                                            \"displayName\": \"Get_Domain_Attributes_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"38\"," +
            "                                            \"displayName\": \"Create_Program_Invocation_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"39\"," +
            "                                            \"displayName\": \"Delete_Program_Invocation_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"40\"," +
            "                                            \"displayName\": \"Start_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"41\"," +
            "                                            \"displayName\": \"Stop_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"42\"," +
            "                                            \"displayName\": \"Resume_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"43\"," +
            "                                            \"displayName\": \"Reset_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"44\"," +
            "                                            \"displayName\": \"Kill_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"45\"," +
            "                                            \"displayName\": \"Get_Program_Invocation_Attributes_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"46\"," +
            "                                            \"displayName\": \"Obtain_File_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"47\"," +
            "                                            \"displayName\": \"Define_Event_Condition_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"48\"," +
            "                                            \"displayName\": \"Delete_Event_Condition_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"49\"," +
            "                                            \"displayName\": \"Get_Event_Condition_Attributes_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"50\"," +
            "                                            \"displayName\": \"Report_Event_Condition_Status_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"51\"," +
            "                                            \"displayName\": \"Alter_Event_Condition_Monitoring_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"52\"," +
            "                                            \"displayName\": \"Trigger_Event_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"53\"," +
            "                                            \"displayName\": \"Define_Event_Action_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"54\"," +
            "                                            \"displayName\": \"Delete_Event_Action_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"55\"," +
            "                                            \"displayName\": \"Get_Event_Action_Attributes_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"56\"," +
            "                                            \"displayName\": \"Report_Event_Action_Status_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"57\"," +
            "                                            \"displayName\": \"Define_Event_Enrollment_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"58\"," +
            "                                            \"displayName\": \"Delete_Event_Enrollment_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"59\"," +
            "                                            \"displayName\": \"Alter_Event_Enrollment_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"60\"," +
            "                                            \"displayName\": \"Report_Event_Enrollment_Status_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"61\"," +
            "                                            \"displayName\": \"Get_Event_Enrollment_Attributes_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"62\"," +
            "                                            \"displayName\": \"Acknowledge_Event_Notification_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"63\"," +
            "                                            \"displayName\": \"Get_Alarm_Summary_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"64\"," +
            "                                            \"displayName\": \"Get_Alarm_Enrollment_Summary_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"65\"," +
            "                                            \"displayName\": \"Read_Journal_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"66\"," +
            "                                            \"displayName\": \"Write_Journal_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"67\"," +
            "                                            \"displayName\": \"Initialize_Journal_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"68\"," +
            "                                            \"displayName\": \"Report_Journal_Status_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"69\"," +
            "                                            \"displayName\": \"Create_Journal_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"70\"," +
            "                                            \"displayName\": \"Delete_Journal_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"71\"," +
            "                                            \"displayName\": \"Get_Capability_List_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"72\"," +
            "                                            \"displayName\": \"File_Open_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"73\"," +
            "                                            \"displayName\": \"File_Read_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"74\"," +
            "                                            \"displayName\": \"File_Close_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"75\"," +
            "                                            \"displayName\": \"File_Rename_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"76\"," +
            "                                            \"displayName\": \"File_Delete_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "                                        {" +
            "                                            \"name\": \"77\"," +
            "                                            \"displayName\": \"File_Directory_Request\"," +
            "                                            \"type\": \"VALUE\"," +
            "                                            \"active\": false," +
            "                                            \"range\": \"NA\"," +
            "                                            \"children\": []" +
            "                                        }," +
            "{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}" +
            "                                    ]" +
            "                                }]},{" +
            "                            \"name\": \"1\"," +
            "                            \"displayName\": \"Confirmed_Response\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"2\"," +
            "                            \"displayName\": \"Confirmed_Error\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"3\"," +
            "                            \"displayName\": \"Unconfirmed\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"4\"," +
            "                            \"displayName\": \"Reject\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"5\"," +
            "                            \"displayName\": \"Cancel_Request\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"6\"," +
            "                            \"displayName\": \"Cancel_Response\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"7\"," +
            "                            \"displayName\": \"Cancel_Error\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"8\"," +
            "                            \"displayName\": \"Initiate_Request\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"9\"," +
            "                            \"displayName\": \"Initiate_Response\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"10\"," +
            "                            \"displayName\": \"Initiate_Error\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"11\"," +
            "                            \"displayName\": \"Conclude_Request\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"12\"," +
            "                            \"displayName\": \"Conclude_Response\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        }," +
            "                        {" +
            "                            \"name\": \"13\"," +
            "                            \"displayName\": \"Conclude_Error\"," +
            "                            \"type\": \"VALUE\"," +
            "                            \"active\": false," +
            "                            \"range\": \"NA\"," +
            "                            \"children\": []" +
            "                        },{name:NONSTANDARD,displayName:NONSTANDARD,type:VALUE,active:false,range:\"NA\",children:[]}" +
            "]}]" +
            "}]}";

}
