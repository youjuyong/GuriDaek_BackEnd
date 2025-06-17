package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import com.example.spring_jpa.api.ErrorCode;
import com.example.spring_jpa.api.Message;
import com.example.spring_jpa.api.v1.repository.TbCommCdRepo;
import com.example.spring_jpa.data.GuriSQL_COMM;
import com.example.spring_jpa.data.GuriSQL_HERO;
import com.example.spring_jpa.data.GuriSQL_SUBWAY;
import com.example.spring_jpa.object.TbCommCd;
import com.example.spring_jpa.utils.HttpConnectionService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/subway")
public class SubWayController implements BackendApi {

    private final GuriSQL_SUBWAY subwaySQL;
    private final HttpConnectionService HttpUtils;
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionService.class);
    StringBuilder sb = new StringBuilder();
    HttpURLConnection con = null;

    @Override
    public void assertConnection() {
    }

    @Override

    public void loadData() {
    }

    @Scheduled(cron = "* * * 1 * *")
    public void getSubwayList() {
        sb.setLength(0);
        sb.append("http://openapi.seoul.go.kr:8088/526a4a717264627739396471456251/json/SearchSTNBySubwayLineInfo/1/1000/");
        con = HttpUtils.getHttpURLConnection(sb.toString(), "GET");
        JSONParser parser = new JSONParser();

        String result = "";

        result = HttpUtils.getHttpRespons(con);

        try {
				JSONObject obj = (JSONObject) parser.parse(result);
                JSONObject obj2 = (JSONObject) obj.get("SearchSTNBySubwayLineInfo");
                JSONArray arr = (JSONArray) obj2.get("row");

				for ( int i = 0; i < arr.size(); i++ ) {
                    Map<String, Object> submap = Maps.newHashMap();
					JSONObject tmp = (JSONObject) arr.get(i);



                    String stationNm = String.valueOf(tmp.get("STATION_NM"));
                    String stationNmEng = String.valueOf(tmp.get("STATION_NM_ENG"));
                    String stationCd = String.valueOf(tmp.get("STATION_CD"));
                    String lineNum = String.valueOf(tmp.get("LINE_NUM"));
                    String stationNmJpn = String.valueOf(tmp.get("STATION_NM_JPN"));
                    String frCode = String.valueOf(tmp.get("FR_CODE"));
                    String stationNmChn = String.valueOf(tmp.get("STATION_NM_CHN"));

                    if ( stationNm.equals("null")  || frCode.equals("null") ) {
                        continue;
                    }
                    submap.put("stationNm", stationNm);
                    submap.put("stationNmEng", stationNmEng);
                    submap.put("stationCd", stationCd);
                    submap.put("lineNum", lineNum);
                    submap.put("stationNmJpn", stationNmJpn);
                    submap.put("frCode", frCode);
                    submap.put("stationNmChn", stationNmChn);
                    subwaySQL.insertStationInfo(submap);
				}
			} catch (Exception ex) {
				LOGGER.error(ex.toString());

			} finally {
                if ( con != null ) con.disconnect();
            }

        }
    final String REGEXP_ONLY_NUM = "^[A-Z]*$";

    @GetMapping(value = "subway-info",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getSubWayInfo(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> subWayList = Arrays.asList();

        try {

            subWayList = subwaySQL.getSubWayInfo(map);

            for ( Map<String, Object > subWayMap : subWayList ) {
                String fcode = String.valueOf(subWayMap.get("FR_CODE"));
                String zCode = "";
                String preCode = "";
                String afterCode = "";
                boolean lengthDif = false;
                boolean isNumber1 = Pattern.matches(REGEXP_ONLY_NUM, fcode.substring(0, 1));

                if ( isNumber1 ) {
                    zCode = fcode.replaceAll("[^a-zA-Z]", "");
                    fcode = fcode.substring(1);

                    int preLength = fcode.length();
                    int afterLength = String.valueOf(Integer.parseInt(fcode)).length();
                    if ( preLength != afterLength ) {
                        lengthDif = true;
                    }
                }

                int preNum   = Integer.parseInt(fcode) - 1;
                int afterNum = Integer.parseInt(fcode) + 1;

                if ( isNumber1 ) {

                    if ( lengthDif )
                    {
                         preCode   = zCode + '0' + String.valueOf(preNum);
                        afterCode  = zCode + '0' + String.valueOf(afterNum);
                    }else
                    {
                         preCode   = zCode + String.valueOf(preNum);
                         afterCode = zCode + String.valueOf(afterNum);
                    }

                } else {
                    preCode   = String.valueOf(preNum);
                    afterCode = String.valueOf(afterNum);
                }

                 Map<String, Object> preDir = subwaySQL.getSubWayDirInfo(preCode);
                 Map<String, Object> afterDir = subwaySQL.getSubWayDirInfo(afterCode);


                if ( preDir == null && afterDir != null ) {
                    subWayMap.put("preStationNm", "");
                    subWayMap.put("afterStationNm", String.valueOf(afterDir.get("STATION_NM")));
                    continue;
                }

                if ( preDir != null && afterDir == null )
                {
                    subWayMap.put("afterStationNm", "");
                    subWayMap.put("preStationNm", String.valueOf(preDir.get("STATION_NM")));
                    continue;
                }

                int preStationNum =  Integer.parseInt(String.valueOf(preDir.get("STATION_CD")));
                int afterStationNum =  Integer.parseInt(String.valueOf(afterDir.get("STATION_CD")));

                if ( preStationNum < afterStationNum ) {

                    subWayMap.put("preStationNm", String.valueOf(preDir.get("STATION_NM")));
                    subWayMap.put("afterStationNm", String.valueOf(afterDir.get("STATION_NM")));
                } else {
                    subWayMap.put("preStationNm", String.valueOf(afterDir.get("STATION_NM")));
                    subWayMap.put("afterStationNm", String.valueOf(preDir.get("STATION_NM")));
                }
            }

            log.info("subWayList !! " + subWayList);
        } catch ( RuntimeException ex ) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        }
        return ResponseEntity.ok(subWayList);
    }


     @GetMapping(value = "subway-favor-info",
            produces = {"application/json"}
     )
     public ResponseEntity<?> subwayFavorList(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> subWayFavorList = Arrays.asList();

        try {
            subWayFavorList = subwaySQL.getSubWayFavorCount(map);
        } catch ( RuntimeException ex ) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        }

        return ResponseEntity.ok(subWayFavorList);
    }

    @DeleteMapping(value = "subway-favor",
            produces = {"application/json"}
     )
     public ResponseEntity<?> deleteSubwayFavor(@RequestParam Map<String, Object> map) {
        int result = 0;

        try {
            result = subwaySQL.deleteSubWayFavor(map);
        } catch ( RuntimeException ex ) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "subway-favor",
            produces = {"application/json"}
     )
     public ResponseEntity<?> putSubwayFavor(@RequestParam Map<String, Object> map) {
        int result = 0;
        try {
            result = subwaySQL.putSubWayFavor(map);
        } catch ( RuntimeException ex ) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "subway-favor-list",
            produces = {"application/json"}
    )
     public ResponseEntity<?> getSubwayFavorList(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> favorList = Lists.newArrayList();

        try {
            favorList = subwaySQL.getSubWayFavorList(map);
        } catch ( RuntimeException ex ) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        }

        return ResponseEntity.ok(favorList);
    }

     @GetMapping(value = "subway-route-info",
            produces = {"application/json"}
     )
     public ResponseEntity<?> getSubwayRouteList(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> subwayList = Lists.newArrayList();

        try {
            subwayList = subwaySQL.getSubWayRouteList(map);
        } catch ( RuntimeException ex ) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        }

        return ResponseEntity.ok(subwayList);
    }
}




