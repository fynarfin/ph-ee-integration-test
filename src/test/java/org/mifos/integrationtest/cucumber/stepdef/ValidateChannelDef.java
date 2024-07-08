package org.mifos.integrationtest.cucumber.stepdef;

import static com.google.common.truth.Truth.assertThat;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.util.ArrayList;
import java.util.List;
import org.mifos.connector.common.gsma.dto.CustomData;
import org.mifos.connector.common.gsma.dto.GsmaTransfer;
import org.mifos.connector.common.gsma.dto.Party;
import org.mifos.integrationtest.common.Utils;
import org.mifos.integrationtest.common.dto.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidateChannelDef extends BaseStepDef {

    @Autowired
    ScenarioScopeState scenarioScopeState;

    Logger logger = LoggerFactory.getLogger(VoucherManagementStepDef.class);

    @Given("I can create a negative TransactionChannelRequestDTO")
    public void iCanCreateANegativeTransactionChannelRequestDTO() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{").append("\"payer\": {").append("\"partyIdInfo\": {").append("\"partyIdType\": \"MSISDN\",")
                .append("\"partyIdentifier\": \"27710101999\"").append("}},").append("\"payee\": {").append("\"partyIdInfo\": {")
                .append("\"partyIdType\": \"MSISDN\",").append("\"partyIdentifier\": \"27710102999\"").append("}},").append("\"amount\": {")
                .append("\"amount\": 230").append("}}");
        String json = jsonBuilder.toString();

        try {
            scenarioScopeState.createTransactionChannelRequestBody = json;
        } catch (Exception e) {
            logger.error("An Exception occurred", e);
        }
    }

    @Given("I can create a negative GsmaTransfer DTO")
    public void iCanCreateANegativeGsmaTransferDTO() {
        GsmaTransfer gsmaTransferDTO = new GsmaTransfer();
        gsmaTransferDTO.setRequestingOrganisationTransactionReference("string");
        gsmaTransferDTO.setSubType("inbound");
        gsmaTransferDTO.setType("transfer");
        gsmaTransferDTO.setAmount("100");
        gsmaTransferDTO.setDescriptionText("string");
        gsmaTransferDTO.setRequestDate("2022-09-28T12:51:19.260+00:00");
        Party payerParty = new Party();
        payerParty.setPartyIdIdentifier("+44999911");
        payerParty.setPartyIdType("MSISDN");
        Party payeeParty = new Party();
        payeeParty.setPartyIdType("accountId");
        payeeParty.setPartyIdIdentifier("organisationid@1234$accountid@3333");
        List<Party> payerList = new ArrayList<>();
        payerList.add(payerParty);
        List<Party> payeeList = new ArrayList<>();
        payeeList.add(payeeParty);
        gsmaTransferDTO.setPayer(payerList);
        gsmaTransferDTO.setPayee(payeeList);
        CustomData customData = new CustomData();
        customData.setKey("string");
        customData.setValue("string");
        List<CustomData> customDataList = new ArrayList<>();
        customDataList.add(customData);
        gsmaTransferDTO.setCustomData(customDataList);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            scenarioScopeState.createGsmaTransferRequestBody = objectMapper.writeValueAsString(gsmaTransferDTO);
        } catch (JsonProcessingException e) {
            logger.error("Unable to convert the DTO : {}", e);
        }
    }

    @Given("I can create a GsmaTransfer DTO")
    public void iCanCreateAGsmaTransferDTO() {
        GsmaTransfer gsmaTransferDTO = new GsmaTransfer();
        gsmaTransferDTO.setRequestingOrganisationTransactionReference("string");
        gsmaTransferDTO.setSubType("inbound");
        gsmaTransferDTO.setType("transfer");
        gsmaTransferDTO.setAmount("100");
        gsmaTransferDTO.setCurrency("SNR");
        gsmaTransferDTO.setDescriptionText("string");
        gsmaTransferDTO.setRequestDate("2022-09-28T12:51:19.260+00:00");
        Party payerParty = new Party();
        payerParty.setPartyIdIdentifier("+44999911");
        payerParty.setPartyIdType("MSISDN");
        Party payeeParty = new Party();
        payeeParty.setPartyIdType("accountId");
        payeeParty.setPartyIdIdentifier("organisationid@1234$accountid@3333");
        List<Party> payerList = new ArrayList<>();
        payerList.add(payerParty);
        List<Party> payeeList = new ArrayList<>();
        payeeList.add(payeeParty);
        gsmaTransferDTO.setPayer(payerList);
        gsmaTransferDTO.setPayee(payeeList);
        CustomData customData = new CustomData();
        customData.setKey("string");
        customData.setValue("string");
        List<CustomData> customDataList = new ArrayList<>();
        customDataList.add(customData);
        gsmaTransferDTO.setCustomData(customDataList);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            scenarioScopeState.createGsmaTransferRequestBody = objectMapper.writeValueAsString(gsmaTransferDTO);
        } catch (JsonProcessingException e) {
            logger.error("Unable to convert the DTO : {}", e);
        }
    }

    @When("I call the post transfer API with expected status of {int}")
    public void iCallThePostTransferAPIWithExpectedStatusOf(int expectedStatus) {
        await().atMost(awaitMost, SECONDS).pollDelay(pollDelay, SECONDS).pollInterval(pollInterval, SECONDS).untilAsserted(() -> {
            RequestSpecification requestSpec = Utils.getDefaultSpec();
            scenarioScopeState.tenant = "gorilla";
            scenarioScopeState.response = RestAssured.given(requestSpec).header("Content-Type", "application/json")
                    .header("Platform-TenantId", scenarioScopeState.tenant).baseUri(channelConnectorConfig.channelConnectorContactPoint)
                    .body(scenarioScopeState.createTransactionChannelRequestBody).expect()
                    .spec(new ResponseSpecBuilder().expectStatusCode(expectedStatus).build()).when()
                    .post(channelConnectorConfig.transferEndpoint).andReturn().asString();

            logger.info("Post Transfer Response: {}", scenarioScopeState.response);
        });
    }

    @When("I call the GSMA transaction API with expected status of {int}")
    public void iCallTheGsmaTransactionAPIWithExpectedStatusOf(int expectedStatus) {
        await().atMost(awaitMost, SECONDS).pollDelay(pollDelay, SECONDS).pollInterval(pollInterval, SECONDS).untilAsserted(() -> {
            RequestSpecification requestSpec = Utils.getDefaultSpec();
            scenarioScopeState.callbackUrl = "https://webhook.site/3cb8ab56-c03d-4251-9911-520f235da8f5";
            scenarioScopeState.amsName = "mifos";
            scenarioScopeState.accountHoldingInstitutionId = "gorilla";
            scenarioScopeState.response = RestAssured.given(requestSpec).header("Content-Type", "application/json")
                    .header("accountHoldingInstitutionId", scenarioScopeState.accountHoldingInstitutionId)
                    .header("amsName", scenarioScopeState.amsName).header("X-CallbackURL", scenarioScopeState.callbackUrl)
                    .baseUri(channelConnectorConfig.channelConnectorContactPoint).body(scenarioScopeState.createGsmaTransferRequestBody)
                    .expect().spec(new ResponseSpecBuilder().expectStatusCode(expectedStatus).build()).when()
                    .post(channelConnectorConfig.gsmaTransactionEndpoint).andReturn().asString();

            logger.info("Gsma transaction Response: {}", scenarioScopeState.response);
        });
    }

    @When("I call the transaction request API with expected status of {int}")
    public void iCallTheTransactionRequestAPIWithExpectedStatusOf(int expectedStatus) {
        await().atMost(awaitMost, SECONDS).pollDelay(pollDelay, SECONDS).pollInterval(pollInterval, SECONDS).untilAsserted(() -> {
            RequestSpecification requestSpec = Utils.getDefaultSpec();
            scenarioScopeState.tenant = "gorilla";
            scenarioScopeState.response = RestAssured.given(requestSpec).header("Content-Type", "application/json")
                    .header("Platform-TenantId", scenarioScopeState.tenant).baseUri(channelConnectorConfig.channelConnectorContactPoint)
                    .body(scenarioScopeState.createTransactionChannelRequestBody).expect()
                    .spec(new ResponseSpecBuilder().expectStatusCode(expectedStatus).build()).when()
                    .post(channelConnectorConfig.transferReqEndpoint).andReturn().asString();

            logger.info("Transaction Request Response: {}", scenarioScopeState.response);
        });
    }

    @And("I should be able to assert the api validation for negative response")
    public void iWillAssertTheFieldsFromValidationResponse() {
        try {
            JsonNode rootNode = objectMapper.readTree(scenarioScopeState.response);

            ErrorDetails errorDetails = objectMapper.treeToValue(rootNode, ErrorDetails.class);

            assertThat(errorDetails.getErrorCode()).isEqualTo("error.msg.schema.validation.errors");
            assertThat(errorDetails.getErrorDescription()).isEqualTo("The request is invalid");

        } catch (Exception e) {
            logger.info("An error occurred : {}", e);
        }
    }

    @Given("I can create an TransactionChannelRequestDTO")
    public void iCreateATransactionChannelRequestDTO() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{").append("\"payer\": {").append("\"partyIdInfo\": {").append("\"partyIdType\": \"MSISDN\",")
                .append("\"partyIdentifier\": \"27710101999\"").append("}},").append("\"payee\": {").append("\"partyIdInfo\": {")
                .append("\"partyIdType\": \"MSISDN\",").append("\"partyIdentifier\": \"27710102999\"").append("}},").append("\"amount\": {")
                .append("\"amount\": 230,").append("\"currency\": \"TZS\"").append("}}");
        String json = jsonBuilder.toString();

        try {
            scenarioScopeState.createTransactionChannelRequestBody = json;
        } catch (Exception e) {
            logger.error("An Exception occurred", e);
        }
    }

    @When("I call the post transfer API having invalid header with expected status of {int}")
    public void iCallThePostTransferAPIHavingInvalidHeaderWithExpectedStatusOf(int expectedStatus) {
        await().atMost(awaitMost, SECONDS).pollDelay(pollDelay, SECONDS).pollInterval(pollInterval, SECONDS).untilAsserted(() -> {
            RequestSpecification requestSpec = Utils.getDefaultSpec();
            scenarioScopeState.tenant = "gorilla";
            scenarioScopeState.response = RestAssured.given(requestSpec).header("Content-Type", "application/json")
                    .header("Platform-TenantId", scenarioScopeState.tenant).header("invalid-header", "test")
                    .baseUri(channelConnectorConfig.channelConnectorContactPoint)
                    .body(scenarioScopeState.createTransactionChannelRequestBody).expect()
                    .spec(new ResponseSpecBuilder().expectStatusCode(expectedStatus).build()).when()
                    .post(channelConnectorConfig.transferEndpoint).andReturn().asString();

            logger.info("Post Transfer Response: {}", scenarioScopeState.response);
        });
    }

    @When("I call the transaction request API having invalid header with expected status of {int}")
    public void iCallTheTransactionRequestAPIHavingInvalidHeaderWithExpectedStatusOf(int expectedStatus) {
        await().atMost(awaitMost, SECONDS).pollDelay(pollDelay, SECONDS).pollInterval(pollInterval, SECONDS).untilAsserted(() -> {
            RequestSpecification requestSpec = Utils.getDefaultSpec();
            scenarioScopeState.tenant = "gorilla";
            scenarioScopeState.response = RestAssured.given(requestSpec).header("Content-Type", "application/json")
                    .header("Platform-TenantId", scenarioScopeState.tenant).header("invalid-header", "test")
                    .baseUri(channelConnectorConfig.channelConnectorContactPoint)
                    .body(scenarioScopeState.createTransactionChannelRequestBody).expect()
                    .spec(new ResponseSpecBuilder().expectStatusCode(expectedStatus).build()).when()
                    .post(channelConnectorConfig.transferReqEndpoint).andReturn().asString();

            logger.info("Transaction Request Response: {}", scenarioScopeState.response);
        });
    }

    @When("I call the gsma transaction API having invalid header with expected status of {int}")
    public void iCallTheGsmaTransactionAPIHavingInvalidHeaderWithExpectedStatusOf(int expectedStatus) {
        await().atMost(awaitMost, SECONDS).pollDelay(pollDelay, SECONDS).pollInterval(pollInterval, SECONDS).untilAsserted(() -> {
            RequestSpecification requestSpec = Utils.getDefaultSpec();
            scenarioScopeState.callbackUrl = "https://webhook.site/3cb8ab56-c03d-4251-9911-520f235da8f5";
            scenarioScopeState.amsName = "mifos";
            scenarioScopeState.accountHoldingInstitutionId = "gorilla";
            scenarioScopeState.response = RestAssured.given(requestSpec).header("Content-Type", "application/json")
                    .header("accountHoldingInstitutionId", scenarioScopeState.accountHoldingInstitutionId)
                    .header("amsName", scenarioScopeState.amsName).header("X-CallbackURL", scenarioScopeState.callbackUrl)
                    .header("invalid-header", "test").baseUri(channelConnectorConfig.channelConnectorContactPoint)
                    .body(scenarioScopeState.createGsmaTransferRequestBody).expect()
                    .spec(new ResponseSpecBuilder().expectStatusCode(expectedStatus).build()).when()
                    .post(channelConnectorConfig.gsmaTransactionEndpoint).andReturn().asString();

            logger.info("Transaction Request Response: {}", scenarioScopeState.response);
        });
    }

    @When("I call the post transfer API without required header with expected status of {int}")
    public void iCallThePostTransferAPINotHavingRequiredHeaderWithExpectedStatusOf(int expectedStatus) {
        await().atMost(awaitMost, SECONDS).pollDelay(pollDelay, SECONDS).pollInterval(pollInterval, SECONDS).untilAsserted(() -> {
            RequestSpecification requestSpec = Utils.getDefaultSpec();
            scenarioScopeState.response = RestAssured.given(requestSpec).header("Content-Type", "application/json")
                    .baseUri(channelConnectorConfig.channelConnectorContactPoint)
                    .body(scenarioScopeState.createTransactionChannelRequestBody).expect()
                    .spec(new ResponseSpecBuilder().expectStatusCode(expectedStatus).build()).when()
                    .post(channelConnectorConfig.transferEndpoint).andReturn().asString();

            logger.info("Post Transfer Response: {}", scenarioScopeState.response);
        });
    }

    @When("I call the transaction request API without required header with expected status of {int}")
    public void iCallTheTransactionRequestAPINotHavingRequiredHeaderWithExpectedStatusOf(int expectedStatus) {
        await().atMost(awaitMost, SECONDS).pollDelay(pollDelay, SECONDS).pollInterval(pollInterval, SECONDS).untilAsserted(() -> {
            RequestSpecification requestSpec = Utils.getDefaultSpec();
            scenarioScopeState.response = RestAssured.given(requestSpec).header("Content-Type", "application/json")
                    .baseUri(channelConnectorConfig.channelConnectorContactPoint)
                    .body(scenarioScopeState.createTransactionChannelRequestBody).expect()
                    .spec(new ResponseSpecBuilder().expectStatusCode(expectedStatus).build()).when()
                    .post(channelConnectorConfig.transferReqEndpoint).andReturn().asString();

            logger.info("Transaction Request Response: {}", scenarioScopeState.response);
        });
    }

    @When("I call the gsma transaction API without required header with expected status of {int}")
    public void iCallTheGsmaTransactionAPINotHavingRequiredHeaderWithExpectedStatusOf(int expectedStatus) {
        await().atMost(awaitMost, SECONDS).pollDelay(pollDelay, SECONDS).pollInterval(pollInterval, SECONDS).untilAsserted(() -> {
            RequestSpecification requestSpec = Utils.getDefaultSpec();
            scenarioScopeState.callbackUrl = "https://webhook.site/3cb8ab56-c03d-4251-9911-520f235da8f5";
            scenarioScopeState.amsName = "mifos";
            scenarioScopeState.response = RestAssured.given(requestSpec).header("Content-Type", "application/json")
                    .header("amsName", scenarioScopeState.amsName).header("X-CallbackURL", scenarioScopeState.callbackUrl)
                    .baseUri(channelConnectorConfig.channelConnectorContactPoint).body(scenarioScopeState.createGsmaTransferRequestBody)
                    .expect().spec(new ResponseSpecBuilder().expectStatusCode(expectedStatus).build()).when()
                    .post(channelConnectorConfig.gsmaTransactionEndpoint).andReturn().asString();

            logger.info("GSMA Transaction Response: {}", scenarioScopeState.response);
        });
    }
}
