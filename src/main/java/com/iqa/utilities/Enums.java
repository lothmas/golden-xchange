//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.iqa.utilities;

public class Enums {
    public Enums() {
    }

    public static enum BankAccountTypeEnum {
        CURRENT("Current"),
        SAVINGS("Savings"),
        BOND("Bond"),
        INVESTMENT("Investment"),
        NONRESIDENT("Non-Resident");

        private String type;

        private BankAccountTypeEnum(String payoutMethodType) {
            this.type = payoutMethodType;
        }

        public String getName() {
            return this.type;
        }
    }

    public enum UserStatusEnum {

        PENDING(1),
        /**
         *
         */
        ACTIVE(2), CANCELLED(3);

        private int intType;

        UserStatusEnum(int type) {
            this.intType = type;
        }

        /**
         *
         * @return
         */
        public int getValue() {
            return intType;
        }

    }

    public static enum BankNameEnum {
        ABSA("ABSA", 632005, "ABSA"),
        AfricanBank("African Bank", 0, ""),
        BankofAthens("Bank of Athens", 0, ""),
        BankofTaiwan("Bank of Taiwan", 0, ""),
        BankofTranskei("Bank of Transkei", 0, ""),
        BidvestBank("Bidvest Bank", 0, ""),
        BolandBank("Boland Bank", 0, ""),
        CapeofGoodHopeBank("Cape of Good Hope Bank", 0, ""),
        CapitecBank("Capitec Bank", 470010, "CAPITEC BANK CPC"),
        CommunityBank("Community Bank", 0, ""),
        FidelityBankLimited("Fidelity Bank Limited", 0, ""),
        FirstNationalBank("First National Bank", 250655, "FNB PREFERRED DEFAULT BRANCH CODE"),
        FrenchBank("French Bank", 0, ""),
        FutureBankCorporation("Future Bank Corporation", 0, ""),
        HabibOverseasBankLimited("Habib Overseas Bank Limited", 0, ""),
        HBZBank("HBZ Bank", 0, ""),
        Investec("Investec", 580105, "INVESTEC BANK GRAYSTON DRIVE"),
        MercantileLisbonBank("Mercantile Lisbon Bank", 0, ""),
        NBS("NBS", 0, ""),
        Nedbank("Nedbank", 198765, ""),
        PeoplesBank("Peoples Bank", 0, ""),
        PermanentBank("Permanent Bank", 0, ""),
        SAPostOffice("SA Post Office", 460005, "POSTBANK DIV OF SA POST OFFICE"),
        ReserveBank("Reserve Bank", 0, ""),
        Sammbou("Sammbou", 0, ""),
        StandardBank("Standard Bank", 20993, "STANDARD BANK SOUTH AFRICA"),
        Swabou("Swabou", 0, ""),
        UnibankLimited("Unibank Limited", 0, "");

        private String type;
        private int intType;
        private String defaultBranchName;

        private BankNameEnum(String payoutMethodType, int branchCode, String branchName) {
            this.type = payoutMethodType;
            this.intType = branchCode;
            this.defaultBranchName = branchName;
        }

        public String getName() {
            return this.type;
        }

        public int getValue() {
            return this.intType;
        }

        public String getDefaultBranchName() {
            return this.defaultBranchName;
        }
    }

    public static enum StatusCodeEnum {
        OK(200, "Successful request"),
        CREATED(202, "Record Created"),
        REDIRECT(302, "Webservice redirect"),
        SEEOTHER(303, "See Other Webservice"),
        NOTAUTHORISED(401, "Not Authorised to access resources"),
        NOTFOUND(404, "The requested resource could not be found"),
        INVALIDSYNTAX(400, "The request cannot be fulfilled due to bad syntax"),
        RANGENOTSATISFIED(416, "Requested Range Not Satisfiable"),
        TOKENEXPIRED(498, "Token Expired / Invalid"),
        DATATYPEMISMATCH(406, "Data type mismathch"),
        INCORRECTLENGTH(411, "Required data length not met"),
        EMPTYVALUE(412, "Empty value not allowed"),
        FORBIDDEN(403, "Forbidden request"),
        UNPROCESSABLE(422, "Unprocessable Entity"),
        SERVERERROR(500, "Internal Server Error");

        private int intType;
        private String stringType;

        private StatusCodeEnum(int statusCode, String statusMessage) {
            this.intType = statusCode;
            this.stringType = statusMessage;
        }

        public int getStatusCode() {
            return this.intType;
        }

        public String getStatusMessage() {
            return this.stringType;
        }
    }


    public static enum CandidateVerificationProgress {
        INPROGRESS(1, "In Progress"),
        COMPLETED(2, "Completed"),
        DROPOUT(3, "Drop Out");

        private int intType;
        private String stringType;

        private CandidateVerificationProgress(int statusCode, String statusMessage) {
            this.intType = statusCode;
            this.stringType = statusMessage;
        }

        public int getStatusCode() {
            return this.intType;
        }

        public String getStatusMessage() {
            return this.stringType;
        }
    }

    public static enum CandidateVerificationOutCome {
        AUTHENTIC(1, "Authentic"),
        NONAUTHENTIC(2, "Not Authentic");

        private int intType;
        private String stringType;

        private CandidateVerificationOutCome(int statusCode, String statusMessage) {
            this.intType = statusCode;
            this.stringType = statusMessage;
        }

        public int getStatusCode() {
            return this.intType;
        }

        public String getStatusMessage() {
            return this.stringType;
        }
    }

    public static enum VerificationRequestProgress {
        SENT(1, "Request Sent"),
        RESPONDED(2, "Request Responded");

        private int intType;
        private String stringType;

        private VerificationRequestProgress(int statusCode, String statusMessage) {
            this.intType = statusCode;
            this.stringType = statusMessage;
        }

        public int getStatusCode() {
            return this.intType;
        }

        public String getStatusMessage() {
            return this.stringType;
        }
    }


    public static enum VerificationRequestPaymentStatus {
        SENT(1, "Request Sent"),
        RESPONDED(2, "Request Responded");

        private int intType;
        private String stringType;

        private VerificationRequestPaymentStatus(int statusCode, String statusMessage) {
            this.intType = statusCode;
            this.stringType = statusMessage;
        }

        public int getStatusCode() {
            return this.intType;
        }

        public String getStatusMessage() {
            return this.stringType;
        }
    }

    public static enum UserTypes {
        INDIVIDUAL(1, "Individual"),
        GOVERNMENT(2, "Government"),
        PARASTATAL(3, "Parastatal"),
        PRIVATE(4, "Private Company");


        private int intType;
        private String stringType;

        private UserTypes(int statusCode, String statusMessage) {
            this.intType = statusCode;
            this.stringType = statusMessage;
        }

        public int getUserValue() {
            return this.intType;
        }

        public String getUserName() {
            return this.stringType;
        }
    }
}

