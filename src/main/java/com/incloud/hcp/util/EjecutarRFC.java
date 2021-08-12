package com.incloud.hcp.util;

import com.incloud.hcp.jco.maestro.dto.*;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class EjecutarRFC {

    private JCoDestination destination;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public EjecutarRFC(){
    }

    public MaestroExport Execute_ZFL_RFC_READ_TABLE(HashMap<String, Object> imports, List<HashMap<String, Object>> optionsParam) throws Exception{
        JCoFunction function = getFunction(Constantes.ZFL_RFC_READ_TABLE);
        setImports(function, imports);
        logger.error("Execute_ZFL_RFC_READ_TABLE_1");;
        JCoParameterList jcoTables = function.getTableParameterList();
        setTable(jcoTables, "OPTIONS", optionsParam);
        function.execute(destination);
        logger.error("Execute_ZFL_RFC_READ_TABLE_2");;

        JCoTable DATA = jcoTables.getTable("DATA");
        JCoTable FIELDS = jcoTables.getTable("FIELDS");
        //JCoTable T_MENSAJE = jcoTables.getTable("FIELDS");
        //T_MENSAJE.setRow(0);
        String mensaje = "Ok";
        logger.error("Execute_ZFL_RFC_READ_TABLE_3");;

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < DATA.getNumRows(); i++) {
            DATA.setRow(i);
            String ArrayResponse[] = DATA.getString().split("\\|");
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            for (int j = 0; j < FIELDS.getNumRows(); j++){
                FIELDS.setRow(j);
                String key = (String) FIELDS.getValue("FIELDNAME");
                Object value = ArrayResponse[j].trim();
                newRecord.put(key, value);
            };
            data.add(newRecord);
        }
        logger.error("Execute_ZFL_RFC_READ_TABLE_4");;

        MaestroExport me = new MaestroExport();
        me.setData(data);
        me.setMensaje(mensaje);
        return me;
    }

    public void setImports(JCoFunction function, HashMap<String, Object> imports){

        JCoParameterList jcoImport = function.getImportParameterList();
        Iterator iterator = imports.entrySet().iterator();
        logger.error("setImports_1");
        while (iterator.hasNext()) {
            Map.Entry tmpImport = (Map.Entry) iterator.next();
            String key = tmpImport.getKey().toString();
            Object value = tmpImport.getValue();
            jcoImport.setValue(key, value);
        }
        logger.error("setImports_2");
    }

    public void setTable(JCoParameterList listTables, String tableName, List<HashMap<String, Object>> data){
        JCoTable tableImport = listTables.getTable(tableName);
        logger.error("setTable_1");
        for (int i = 0; i < data.size(); i++){
            tableImport.appendRow();
            HashMap<String, Object> record = data.get(i);
            Iterator iterator = record.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry tmpImport = (Map.Entry) iterator.next();
                String key = tmpImport.getKey().toString();
                Object value = tmpImport.getValue();
                tableImport.setValue(key, value);
            }
        }
        logger.error("setTable_2");

    }

    public JCoFunction getFunction (String FunctionName) throws Exception{
        destination = getDestination();
        JCoRepository repo = destination.getRepository();
        logger.error("getFunction_1");
        return repo.getFunction(FunctionName);
    }

    public JCoDestination getDestination() throws Exception{
        return JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
    }

    public List<EmbarcacionDto> Execute_ZFL_RFC_CONS_EMBARCA(HashMap<String, Object> imports, List<HashMap<String, Object>> optionsParam, List<HashMap<String, Object>> optionsParam2) throws Exception{

        JCoFunction function = getFunction(Constantes.ZFL_RFC_CONS_EMBARCA);
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_1");
        setImports(function, imports);
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_2");

        JCoParameterList jcoTables = function.getTableParameterList();
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_3");
        setTable(jcoTables, "P_OPTIONS", optionsParam);
        setTable(jcoTables, "P_OPTIONS2", optionsParam2);
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_4");
        function.execute(destination);
        JCoTable DATA = jcoTables.getTable(Tablas.STR_EMB);
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_5");

        String mensaje = "Ok";


        List<EmbarcacionDto> ListaEmb= new ArrayList<EmbarcacionDto>();
        for (int i = 0; i < DATA.getNumRows(); i++) {
            DATA.setRow(i);
            EmbarcacionDto dto = new EmbarcacionDto();

            dto.setCDEMB(DATA.getString("CDEMB"));
            dto.setNMEMB(DATA.getString("NMEMB"));
            dto.setMREMB(DATA.getString("MREMB"));
            dto.setCDEMP(DATA.getString("CDEMP"));
            dto.setCDSPE(DATA.getString("CDSPE"));
            dto.setDSSPE(DATA.getString("DSSPE"));
            dto.setESEMB(DATA.getString("ESEMB"));
            dto.setNRTRI(DATA.getString("NRTRI"));
            dto.setWERKS(DATA.getString("WERKS"));
            dto.setCVADM(DATA.getString("CVADM"));
            dto.setCPSDM(DATA.getString("CPSDM"));
            dto.setCVPMS(DATA.getString("CVPMS"));
            dto.setCPPMS(DATA.getString("CPPMS"));
            dto.setLIFNR(DATA.getString("LIFNR"));
            dto.setINPRP(DATA.getString("INPRP"));
            dto.setKUNNR(DATA.getString("KUNNR"));
            dto.setNAME1(DATA.getString("NAME1"));
            dto.setSTCD1(DATA.getString("STCD1"));
            dto.setTCBPS(DATA.getString("TCBPS"));
            dto.setCNVPS(DATA.getString("CNVPS"));
            dto.setFCVPS(DATA.getString("FCVPS"));

            ListaEmb.add(dto);
            //lista.add(param);
        }
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_6");
        return ListaEmb;
    }

    public Mensaje Exec_ZFL_RFC_ACT_CAMP_TAB(STR_SETDto str_setDto, String Tabla)throws Exception{

        logger.error("EditarCaptanques_1");;
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        //JCo
        logger.error("EditarCaptanques_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("EditarCaptanques_3");;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_ACT_CAMP_TAB);
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("P_USER", str_setDto.getP_USER());
        logger.error("EditarCaptanques_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable(Tablas.STR_SET);
        tableImport.appendRow();
        logger.error("EditarCaptanques_5");;
        tableImport.setValue("NMTAB", Tabla);
        tableImport.setValue("CMSET", str_setDto.getCMSET());
        tableImport.setValue("CMOPT", str_setDto.getCMOPT());
        //Ejecutar Funcion
        logger.error("NMTAB " +  Tabla);
        logger.error("CMSET", str_setDto.getCMSET());
        logger.error("CMOPT", str_setDto.getCMOPT());
        stfcConnection.execute(destination);
        logger.error("EditarCaptanques_6");

        Mensaje msj= new Mensaje();
        msj.setMensaje("Ok");

        //Recuperar Datos de SAP
        /*
        JCoTable tableExport = tables.getTable(Tablas.T_MENSAJE);
        tableExport.setRow(0);
        mensaje=tableExport.getString("DSMIN");

        if(mensaje==""){
            mensaje="Ok";
        }*/


        return msj;
    }

    public MensajeDto Execute_ZFL_RFC_UPDATE_TABLE(HashMap<String, Object> imports, String data) throws Exception{

        JCoFunction function = getFunction(Constantes.ZFL_RFC_UPDATE_TABLE);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_1");
        setImports(function, imports);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_2");
        JCoParameterList jcoTables = function.getTableParameterList();
        JCoTable tableImport = jcoTables.getTable("T_DATA");
        tableImport.appendRow();
        tableImport.setValue("DATA", data);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_3");
        function.execute(destination);
        JCoTable tableExport = jcoTables.getTable("T_MENSAJE");

        MensajeDto dto = new MensajeDto();
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);


            dto.setMANDT(tableExport.getString("MANDT"));
            dto.setCMIN(tableExport.getString("CMIN"));
            dto.setCDMIN(tableExport.getString("CDMIN"));
            dto.setDSMIN(tableExport.getString("DSMIN"));

        }
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_4");


        return dto;
    }

    public List<BusquedaEmbarcacionDto> Excute_ZFL_RFC_LECT_MAES_EMBAR(HashMap<String, Object> imports, List<HashMap<String, Object>> optionsParam)throws Exception{

        JCoFunction function = getFunction(Constantes.ZFL_RFC_LECT_MAES_EMBAR);
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_1");
        setImports(function, imports);
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_2");

        JCoParameterList jcoTables = function.getTableParameterList();
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_3");
        setTable(jcoTables, "OPTIONS", optionsParam);

        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_4");
        function.execute(destination);
        JCoTable DATA = jcoTables.getTable(Tablas.S_DATA);
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_5");

        String mensaje = "Ok";


        List<BusquedaEmbarcacionDto> ListaEmb= new ArrayList<BusquedaEmbarcacionDto>();
        for (int i = 0; i < DATA.getNumRows(); i++) {
            DATA.setRow(i);
            BusquedaEmbarcacionDto dto = new BusquedaEmbarcacionDto();

            dto.setCDEMB(DATA.getString("CDEMB"));
            dto.setNMEMB(DATA.getString("NMEMB"));
            dto.setMREMB(DATA.getString("MREMB"));
            dto.setNRPER(DATA.getString("NRPER"));
            dto.setCDEMP(DATA.getString("CDEMP"));
            dto.setNAME1(DATA.getString("NAME1"));
            dto.setCDSPE(DATA.getString("CDSPE"));
            dto.setDSSPE(DATA.getString("DSSPE"));
            dto.setREGIM(DATA.getString("REGIM"));
            dto.setCDTPR(DATA.getString("CDTPR"));
            dto.setCDTCS(DATA.getString("CDTCS"));
            dto.setCVADM(DATA.getString("CVADM"));
            dto.setCPSDM(DATA.getString("CPSDM"));
            dto.setCVPMS(DATA.getString("CVPMS"));
            dto.setCPPMS(DATA.getString("CPPMS"));
            dto.setCDGCP(DATA.getString("CDGCP"));
            dto.setDSGCP(DATA.getString("DSGCP"));
            dto.setCDGFL(DATA.getString("CDGFL"));
            dto.setDSGFL(DATA.getString("DSGFL"));
            dto.setCDGAC(DATA.getString("CDGAC"));
            dto.setWERKS(DATA.getString("WERKS"));
            dto.setNAME(DATA.getString("NAME"));
            dto.setLGORT(DATA.getString("LGORT"));
            dto.setLGOBE(DATA.getString("LGOBE"));
            dto.setCDALS(DATA.getString("CDALS"));
            dto.setKOSTL(DATA.getString("KOSTL"));
            dto.setNRUBC(DATA.getString("NRUBC"));
            dto.setCOCSC(DATA.getString("COCSC"));
            dto.setESLOR(DATA.getString("ESLOR"));
            dto.setMANGA(DATA.getString("MANGA"));
            dto.setPNTAL(DATA.getString("PNTAL"));
            dto.setTRBRT(DATA.getString("TRBRT"));
            dto.setTRNET(DATA.getString("TRNET"));
            dto.setESEMB(DATA.getString("ESEMB"));
            dto.setIMAGN(DATA.getString("IMAGN"));
            dto.setINPRP(DATA.getString("INPRP"));
            dto.setFCEMB(DATA.getString("FCEMB"));
            dto.setUCEMB(DATA.getString("UCEMB"));
            dto.setFMEMB(DATA.getString("FMEMB"));
            dto.setUMEMB(DATA.getString("UMEMB"));
            dto.setNRTRI(DATA.getString("NRTRI"));
            dto.setCDTEM(DATA.getString("CDTEM"));
            dto.setTCBPS(DATA.getString("TCBPS"));
            dto.setCNVPS(DATA.getString("CNVPS"));
            dto.setFCVPS(DATA.getString("FCVPS"));
            dto.setINOOC(DATA.getString("INOOC"));
            dto.setCPNCN(DATA.getString("CPNCN"));
            dto.setCPNSR(DATA.getString("CPNSR"));
            dto.setCLGFL(DATA.getString("CLGFL"));
            dto.setEMPTO(DATA.getString("EMPTO"));
            dto.setTONAB(DATA.getString("TONAB"));
            dto.setAQBRT(DATA.getString("AQBRT"));
            dto.setAQNET(DATA.getString("AQNET"));
            dto.setCDTAN(DATA.getString("CDTAN"));
            dto.setNRORD(DATA.getString("NRORD"));
            dto.setCOCAS(DATA.getString("COCAS"));
            dto.setCOSUP(DATA.getString("COSUP"));
            dto.setCDSVE(DATA.getString("CDSVE"));
            dto.setDSSVE(DATA.getString("DSSVE"));


            ListaEmb.add(dto);
            //lista.add(param);
        }
        logger.error("Execute_ZFL_RFC_CONS_EMBARCA_6");

        return ListaEmb;
    }
}
