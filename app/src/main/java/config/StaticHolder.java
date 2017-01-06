package config;

import android.app.Activity;

import com.android.volley.RequestQueue;
import com.hs.userportal.Services;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class StaticHolder {


    /*Server Locations */

     public final String BASE_URL = "https://api.healthscion.com/WebServices/LabService.asmx/"; //LIVE //TODO commented by ayaz
   // public final String BASE_URL = "http://ec2-54-169-123-240.ap-southeast-1.compute.amazonaws.com/WebServices/LabService.asmx/"; // testing
   // public final String BASE_URL = "http://192.168.1.11/WebServices/LabService.asmx/"; //LOCAL //TODO opened by ayaz
    //  public final String LIVELOGIN_URL = "https://l141702.cloudchowk.com/";     //live
    // public  final String LIVELOGIN_URL = "https://d141702.cloudchowk.com/";//demo
    // public final String LIVELOGIN_URL = "http://192.168.1.56:8085/";// local
   // public final String BASE_URL1 = "http://192.168.1.11/";//local //TODO opened by ayaz
    public final String BASE_URL1 = "https://api.healthscion.com/"; //TODO commented by ayaz
    // public final String BASE_URL1 = "http://ec2-54-169-123-240.ap-southeast-1.compute.amazonaws.com/";// testing

    //APIs URLs
    //  public static final String SIGNUP = BASE_URL + "SignUpByPatient";
    //  public static final String CHECKEMAILIDEXISTS = BASE_URL + "CheckEmailIdIsExistMobile";
    // public static final String CHECKFBEMAILEXISTS = BASE_URL + "EmailIdExistFacebook";
    //  public static final String GETUSERCODE = BASE_URL + "GetUserCodeFromEmail";
    //  public static final String LAB_TEST = BASE_URL + "GetLabByTest";
    // public static final String TEST_LAB = BASE_URL + "GetTestByLab";
    // public static final String PRICE_TEST = BASE_URL + "GetTestPriceList";
    // public static final String PRICE_TEST = BASE_URL + "FindLabsTest";
    //  public static final String SEND_LABCONTACT = BASE_URL + "SendLabContactDetail";
    //  public static final String UPDATECONTACT = BASE_URL + "UpdateContact";
    // public static final String SAMPLEPICKUP = BASE_URL + "SamplePickUp";
    //public static final String BOOKTEST = BASE_URL + "BookTest";
    // public static final String COUPON_TESTMAIL = BASE_URL + "SendCouponWithTestViaEmailNew";
    //  public static final String COUPON_TESTSMS = BASE_URL + "SendCouponWithTestViaSMSNew";
    // public static final String BOOKTEST = BASE_URL + "BookTestNew";
    // public static final String GETLABLIST = BASE_URL + "GetLabList";
    //  public static final String ALLTESTDATA = BASE_URL + "GetAllTestData";
    //  public static final String IMAGEDATA = BASE_URL + "GetPhotographData";
    // public static final String DOCTORDATA = BASE_URL + "GetDoctorData";
    // public static final String CENTREDATA = BASE_URL + "GetCentreData";
    // public static final String UPLOADPRESCRIPTION = BASE_URL + "UploadPrescriptionMail";
    // public static final String COUPON_TESTSMS = BASE_URL + "SendCouponWithTestViaSMS";
    //  public static final String COUPON_SMS = BASE_URL + "SendCouponWithOutTestViaSMS";
    //  public static final String COUPON_TESTMAIL = BASE_URL + "SendCouponWithTestViaEmail";
    // public static final String COUPON_EMAIL = BASE_URL + "SendCouponWithOutTestViaEmail";
    // public static final String GENERATE_COUPON = BASE_URL + "GenerateCoupon";
    //public static final String GENERATE_COUPON = BASE_URL + "GenerateCouponNew";
    // public static final String GENERATE_COUPON = BASE_URL + "GenerateCouponNo";
    //  public static final String pkg_testdetails = BASE_URL + "SinglePackageDetails";
    //  public static final String ORDER_HISTORY = BASE_URL + "GetOrderHistoryDetails";
    // public static final String APPLY_PROMOCODE_URL = BASE_URL + "applypromocode";
    // public static final String ORDER_DETAILS = BASE_URL + "GetFilesForOrderID";
    //  public static final String RESEND_CONFIRMATION = BASE_URL + "IsContactNoExists";
    // public static final String SENDALL_REPORT = BASE_URL + "SendAllReportToUser";
    //  public static final String INVOICE = BASE_URL + "GenerateInvoice";
    //  public static final String CHECKLABAREA = BASE_URL + "CheckLabrangefrom_area";

    //public static final String LOGIN = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/LogIn";
    // public static final String VAULTNEW = LIVELOGIN_URL + "PatientModule/PatientService.asmx/PatientFileVaultNew";
    // public static final String CHECKEMAILIDEXISTS2 = LIVELOGIN_URL + "PatientModule/PatientService.asmx/CheckEmailIdIsExist";
    // public static final String CHECKUSERNAMEEXISTS = LIVELOGIN_URL + "PatientModule/PatientService.asmx/CheckAliasExistMobile";
    //  public static final String CHECKEMAIL_LOGIN = LIVELOGIN_URL + "PatientModule/PatientService.asmx/CheckEmailIdIsExistMobile";
    // public static final String CREDETIALDETAILS = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/GetCredentialDetails";
    //  public static final String PATIENTDISCLAIMER = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/PatientDisclaimer";
    // public static final String AUTHENTICATION = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/IsUserAuthenticated";
    // public static final String TERMS_CONDITIONS = LIVELOGIN_URL + "StaffModule/StaffService.asmx/agreeTermsCondition";
    //  public static final String LOGOUT = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/LogOutIOS";
    //  public static final String AUTOAREASEARCH = LIVELOGIN_URL + "SupplierModule/SupplierMasterService.asmx/GetAutoAreaSearch";
    //  public static final String ALIASEXIST = LIVELOGIN_URL + "CommonMasterModule/CommonMasterService.asmx/checkAliasExist";
    // public static final String PATIENTSIGNUP = LIVELOGIN_URL + "PatientModule/PatientService.asmx/SignUpPatient";
    //  public static final String PATIENTHISTORY = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetPatientHistory";
    //  public static final String CHANGEPASSWORD = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/ChangePasswordIOS";
    //  public static final String OTHERS_AREA = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetOthersFromArea";
    //  public static final String ALLVACCINES = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetAllVaccines";
    //  public static final String PATIENTALERT = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetPatientAlertList";
    //  public static final String REGISTER = LIVELOGIN_URL + "laboratorymodule/LISService.asmx/Register";
    //  public static final String GETADVISE = LIVELOGIN_URL + "LaboratoryModule/LISService.asmx/GetAdvisedInvestigationDetailMobile";
    // public static final String PATIENTCASEDETAILS = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetAllLisPatientCaseDetailMobile";
    // public static final String GETPATIENT = LIVELOGIN_URL + "PatientModule/PatientBedAssignmentService.asmx/GetPatient";
    // public static final String TESTREPORT = LIVELOGIN_URL + "LaboratoryModule/LISService.asmx/GetpatienttestReport";
    //  public static final String FORGOTPASSWORD = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/ForgotPassword";
    // public static final String NEEDHELP = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/NeedHelp";
    // public static final String COMPLETEDTESTDETAILS = LIVELOGIN_URL + "LaboratoryModule/LISInvestigationWorklistService.asmx/GetAllCompletedTestDetailsOfPatient";
    //  public static final String GRAPHREPORT = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetPatientTestRangeDataMobile";
    //  public static final String PATIENTVERIFICATION = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetPatientVerification";
    // public static final String CLINICEMAILVERIFICATION = LIVELOGIN_URL + "PatientModule/PatientService.asmx/EmailVerificationClinic";
    // public static final String RESENDVERIFYCODE = LIVELOGIN_URL + "PatientModule/PatientService.asmx/ResendSmsVerifyCodeClinic";
    //  public static final String CHECKVERIFYCODE = LIVELOGIN_URL + "PatientModule/PatientService.asmx/CheckVerifyCodeClinic";
    //  public static final String CITYLIST1 = LIVELOGIN_URL + "CommonMasterModule/CommonMasterService.asmx/GetCityList1";
    // public static final String CITYLIST = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetCityList";
    //  public static final String GETSTATE = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetStateFromCity";
    // public static final String GETCOUNTRY = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetCountryList";
    // public static final String STATELIST = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetStateList";
    //public static final String PDFREPORT = LIVELOGIN_URL + "LaboratoryModule/lisservice.asmx/GetPatientTestReportMobile";
    // public static final String GETNATION = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetAllNationality";
    //  public static final String LINK_FB = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/FacebookLinked";
    // public static final String LOGIN_FB = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/FacebookLoginMobile";
    // public static final String UNLINK_FB = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/FacebookUnLinked";
    //  public static final String USERDETAILS = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/GetUserDeatils";
    //  public static final String TESTIMAGE_CASES = LIVELOGIN_URL + "LaboratoryModule/LISService.asmx/GetPatientTestImagesInCase";
    // public static final String UPDATEIMAGE = LIVELOGIN_URL + "laboratorymodule/LISService.asmx/UpdateImage";
    //  public static final String GETPDF = LIVELOGIN_URL + "LaboratoryModule/LISService.asmx/GetpatienttestReportAndroid";
    //  public static final String UPLOADIMG_FILEVAULT = LIVELOGIN_URL + "PatientModule/PatientService.asmx/PatientFileVault";
    // public static final String USERDISCLAIMER = LIVELOGIN_URL + "StaffModule/StaffService.asmx/GetUserDisclaimer";
    //  public static final String USERDETAIL_MOBILE = LIVELOGIN_URL + "CommonMasterModule/CommonMasterService.asmx/GetUserDetailsFromContactNoMobileService";
    // public static final String PATIENTFILES = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetPatientFiles";
    //  public static final String LOADVAULT = LIVELOGIN_URL + "Patient/loadVaultMobile";
    //  public static final String DISTINCTTAGS = LIVELOGIN_URL + "Patient/getDistinctTags";
    //  public static final String DELETE_FILES = LIVELOGIN_URL + "PatientModule/PatientService.asmx/DeletePatientFiles";
    // public static final String PATIENT_IMG_CASES = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetPatientImagesInCase";

    //  public static final String allPackages = BASE_URL + "AllPackage";
    // public static final String HOME_PACKAGE_URL = BASE_URL + "HomePackage";
    public static ArrayList<HashMap<String, String>> allPackageslist = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> finalOrderedListAlways = new ArrayList<HashMap<String, String>>();

    Services_static serviceName;
    Activity context;
    JSONObject sendData;
    String[] url_parts = {
            "PatientModule/PatientService.asmx/", "WebServices/CredentialsService.asmx/", "StaffModule/StaffService.asmx", "SupplierModule/SupplierMasterService.asmx/",
            "CommonMasterModule/CommonMasterService.asmx/", "CommonMasterModule/UIService.asmx", "laboratorymodule/LISService.asmx/", "PatientModule/PatientBedAssignmentService.asmx/",
            "CredentialsModule/CredentialService.asmx/", "LaboratoryModule/LISInvestigationWorklistService.asmx/"};

    public StaticHolder(Activity activity, Services_static serviceName, JSONObject sendData) {
        this.serviceName = serviceName;
        this.context = activity;
        this.sendData = sendData;
    }

    public StaticHolder(Activity activity, Services_static serviceName) {
        this.serviceName = serviceName;
        this.context = activity;
    }

    public StaticHolder(Services_static serviceName) {
        this.serviceName = serviceName;
    }

    public String request_Url() {
        String url = null;
        switch (serviceName) {

            case CheckEmailIdIsExistMobile:
                //url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/CheckEmailIdIsExistMobile";
                url = BASE_URL + "CheckEmailIdIsExistMobile";
                break;
            case GetCredentialDetails:
                //url = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/GetCredentialDetails";
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/GetCredentialDetails";
                break;
          /*  case SignUpPatient:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/SignUpPatient";
                break;*/
            case LogOutIOS:
                // url = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/LogOutIOS";
                url = BASE_URL1 + "WebServices/CredentialService.asmx/LogOutIOS";
                break;
            case ChangePasswordIOS:
                //url = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/ChangePasswordIOS";
                url = BASE_URL1 + "WebServices/CredentialService.asmx/ChangePasswordIOS";
                break;
            /*case Register:
                url = LIVELOGIN_URL + "laboratorymodule/LISService.asmx/Register";
                break;*/
            case GetUserDisclaimer:
                // url = LIVELOGIN_URL + "StaffModule/StaffService.asmx/GetUserDisclaimer";
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/GetUserDisclaimer";
                break;
            case FacebookLinked:
                //url = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/FacebookLinked";
                url = BASE_URL1 + "WebServices/CredentialService.asmx/FacebookLinked";
                break;
           /* case FacebookLoginMobile:
                url = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/FacebookLoginMobile";
                break;*/
          /*  case FacebookUnLinked:
                url = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/FacebookUnLinked";
                break;*/
            case GetUserDeatils:
                //url = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/GetUserDeatils";
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/GetUserDeatils";
                break;
            case ForgotPassword:
                // url = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/ForgotPassword";
                url = BASE_URL + "ForgotPassword";
                break;
            case NeedHelp:
                // url = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/NeedHelp";
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/NeedHelp";
                break;

            case PatientDisclaimer:
                //url = LIVELOGIN_URL + "CredentialsModule/CredentialService.asmx/PatientDisclaimer";
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/PatientDisclaimer";
                break;
            case EmailIdExistFacebook:
                url = BASE_URL + "EmailIdExistFacebook";
                break;
            case GetUserCodeFromEmail:
                url = BASE_URL + "GetUserCodeFromEmail";
                break;

            case GetPatientTestImagesInCase:
                url = BASE_URL + "GetPatientTestImagesInCase";
                break;
          /*  case PatientFileVault:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/PatientFileVault";
                break;*/
            case UpdateImage:
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/UpdateImage";
                break;
            case GetpatienttestReportAndroid:
                url = "https://api.healthscion.com/WebServices/HTMLReports.asmx/GetpatienttestReportHTMLAndroid"; //TODO ayaz uncomment it on live
                //url = "https://api.healthscion.com/WebServices/HTMLReports.asmx/GetpatienttestReportHTMLAndroid";
               // url = "http://192.168.1.11/WebServices/HTMLReports.asmx/GetpatienttestReportHTMLAndroid"; // Local
                //http://192.168.1.202:86/WebServices/HTMLReports.asmx
                break;
            case GetUserDetailsFromContactNoMobileService:
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/GetUserDetailsFromContactNoMobileService";
                break;
        /*    case GetPatientFiles:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetPatientFiles";
                break;
            case loadVaultMobile:
                url = LIVELOGIN_URL + "Patient/loadVaultMobile";
                break;
            case getDistinctTags:
                url = LIVELOGIN_URL + "Patient/getDistinctTags";
                break;
            case DeletePatientFiles:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/DeletePatientFiles";
                break;
            case GetPatientImagesInCase:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetPatientImagesInCase";
                break;
            case GetAutoAreaSearch:
                url = LIVELOGIN_URL + "SupplierModule/SupplierMasterService.asmx/GetAutoAreaSearch";
                break;
            case checkAliasExist:
                url = LIVELOGIN_URL + "CommonMasterModule/CommonMasterService.asmx/checkAliasExist";
                break;*/
            case GetLabByTest:
                url = BASE_URL + "GetLabByTest";
                break;
            case GetAllObjectFromS3:
                url = BASE_URL + "GetAllObjectFromS3";
                break;
            case FindLabsTest:
                url = BASE_URL + "FindLabsTest";
                break;
          /*  case GetPatientHistory:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetPatientHistory";
                break;
            case GetOthersFromArea:
                url = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetOthersFromArea";
                break;
            case GetAllVaccines:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetAllVaccines";
                break;
            case GetPatientAlertList:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/GetPatientAlertList";
                break;*/
            case getBasicDetails:
                url = BASE_URL + "getBasicDetails";
                break;
            case GetAdvisedInvestigationDetailMobile:
                url = BASE_URL + "GetAdvisedInvestigationDetailMobile";
                break;
            case GetAllLisPatientCaseDetailMobileNew:
                url = BASE_URL + "GetAllLisPatientCaseDetailMobileNew";
                break;
            case UploadPrescriptionMail:
                url = BASE_URL + "UploadPrescriptionMail";
                break;
            case SendCouponWithTestViaSMSNew:
                url = BASE_URL + "SendCouponWithTestViaSMSNew";
                break;
            case SendCouponWithOutTestViaSMS:
                url = BASE_URL + "SendCouponWithOutTestViaSMS";
                break;
            case SendCouponWithTestViaEmailNew:
                url = BASE_URL + "SendCouponWithTestViaEmailNew";
                break;
            case SendCouponWithOutTestViaEmail:
                url = BASE_URL + "SendCouponWithOutTestViaEmail";
                break;
            case GetPatientTestRangeDataMobile:
                url = BASE_URL + "GetPatientTestRangeDataMobile";
                break;
            case GetPatientVerification:
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/GetPatientVerification";
                break;
            case EmailVerificationClinic:
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/SendMailiOS";
                break;

            case GenerateCouponNo:
                url = BASE_URL + "GenerateCouponNo";
                break;
/*
            case PatientFileVaultNew:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/PatientFileVaultNew";
                break;

            case CheckEmailIdIsExist:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/CheckEmailIdIsExist";
                break;*/
            case CheckAliasExistMobile:
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/CheckAliasExistMobile";
                break;
            case IsUserNameAliasExists:
                url = BASE_URL + "IsUserNameAliasExists";
                break;
          /*  case GetPatientTestReportMobile:
                url = LIVELOGIN_URL + "LaboratoryModule/lisservice.asmx/GetPatientTestReportMobile";
                break;*/
            case GetTestByLab:
                url = BASE_URL + "GetTestByLab";
                break;
            case SendLabContactDetail:
                url = BASE_URL + "SendLabContactDetail";
                break;
            case UpdateContact:
                url = BASE_URL + "UpdateContact";
                break;
            case SamplePickUp:
                url = BASE_URL + "SamplePickUp";
                break;
            case GetLabList:
                url = BASE_URL + "GetLabList";
                break;
            case GetAllTestData:
                url = BASE_URL + "GetAllTestData";
                break;
            case GetPhotographData:
                url = BASE_URL + "GetPhotographData";
                break;
            case GetDoctorData:
                url = BASE_URL + "GetDoctorData";
                break;
            case GetCentreData:
                url = BASE_URL + "GetCentreData";
                break;
            case SinglePackageDetails:
                url = BASE_URL + "SinglePackageDetails";
                break;
            case GetOrderHistoryDetails:
                url = BASE_URL + "GetOrderHistoryDetails";
                break;
            case applypromocode:
                url = BASE_URL + "applypromocode";
                break;
            case GetFilesForOrderID:
                url = BASE_URL + "GetFilesForOrderID";
                break;
            case IsContactNoExists:
                url = BASE_URL + "IsContactNoExists";
                break;
            case BookTestNew:
                url = BASE_URL + "BookTestNew";
                break;
            case SendAllReportToUser:
                url = BASE_URL + "SendAllReportToUser";
                break;
            case GenerateInvoice:
                url = BASE_URL + "GenerateInvoice";
                break;
            case CheckLabrangefrom_area:
                url = BASE_URL + "CheckLabrangefrom_area";
                break;
            case IsUserAuthenticated:
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/IsUserAuthenticated";
                break;
            case agreeTermsCondition:
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/agreeTermsCondition";
                break;
           /* case GetPatient:
                url = LIVELOGIN_URL + "PatientModule/PatientBedAssignmentService.asmx/GetPatient";
                break;
            case GetpatienttestReport:
                url = LIVELOGIN_URL + "LaboratoryModule/LISService.asmx/GetpatienttestReport";
                break;
            case GetAllCompletedTestDetailsOfPatient:
                url = LIVELOGIN_URL + "LaboratoryModule/LISInvestigationWorklistService.asmx/" +
                        "GetAllCompletedTestDetailsOfPatient";
                break;
            case ResendSmsVerifyCodeClinic:
                url = LIVELOGIN_URL + "PatientModule/PatientService.asmx/ResendSmsVerifyCodeClinic";
                break;
            case GetCityList1:
                url = LIVELOGIN_URL + "CommonMasterModule/CommonMasterService.asmx/GetCityList1";
                break;
            case GetCityList:
                url = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetCityList";
                break;
            case GetStateFromCity:
                url = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetStateFromCity";
                break;*/

          /*  case GetCountryList:
                url = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetCountryList";
                break;*/
          /*  case GetAllNationality:
                url = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetAllNationality";
                break;*/
            case CheckVerifyCodeClinic:
                url = BASE_URL1 + "WebServices/CredentialsService.asmx/CheckVerifyCode";
                break;

            case CallmePatient:
                url = BASE_URL + "CallmePatient";
                break;
            case CancelOrder:
                url = BASE_URL + "CancelOrder";
                break;
            case AllPackage:
                url = BASE_URL + "AllPackage";
                break;
            case HomePackage:
                url = BASE_URL + "HomePackage";
                break;
            /*case GetStateList:
                url = LIVELOGIN_URL + "CommonMasterModule/UIService.asmx/GetStateList";
                break;*/
            case BASE_URL:
                //  url = "http://192.168.1.202:86/WebServices/LabService.asmx/"; //local
                url = "https://api.healthscion.com/WebServices/LabService.asmx/"; //live
                // url = "http://ec2-54-169-123-240.ap-southeast-1.compute.amazonaws.com/"; //testing

                break;
            /*case LIVELOGIN_URL:

                 url = "http://192.168.1.56:8085/"; //local  dheer   dheer@123
                //  url ="http://192.168.1.107/";
               // url = "https://l141702.cloudchowk.com/";//live
                //   url = "https://d141702.cloudchowk.com/";//demo   /rahul2  androidios
                break;*/
            case saveOtherDetail:
                url = BASE_URL + "saveOtherDetail";
                break;

            case CreateFolder:
                url = BASE_URL + "CreateFolder";
                break;

            case CreateLockFolder:
                url = BASE_URL + "CreateLockFolder";
                break;

            case DeleteObject:
                url = BASE_URL + "DeleteObject";
                break;
            case AcceptRequest:
                url = BASE_URL + "AcceptRequest";
                break;
            case MoveObject:
                url = BASE_URL + "MoveObject";
                break;

            case deleteSingularDetails:
                url = BASE_URL + "deleteSingularDetails";
                break;

            case saveHealthDetail:
                url = BASE_URL + "saveHealthDetail";
                break;

            case getAllergies:
                url = BASE_URL + "getAllergies";
                break;

            case GetCountryList:
                url = BASE_URL + "getCountries";
                break;
            case getNationality:
                url = BASE_URL + "getNationality";
                break;
            case saveBasicDetail:
                url = BASE_URL + "saveBasicDetail";
                break;
            case UploadProfilePic:
                url = BASE_URL + "UploadProfilePic";
                break;
            case GetMember:
                url = BASE_URL + "GetMember";
                break;
            case AddMember:
                url = BASE_URL + "AddMember";
                break;
            case IsContactExist:
                url = BASE_URL + "IsContactExist";
                break;
            case GetMemberRecords:
                url = BASE_URL + "GetMemberRecords";
                break;
            case getpatientHistoryDetails:
                url = BASE_URL + "getpatientHistoryDetails";
                break;
            case Updatepatientbloodgroup:
                url = BASE_URL + "Updatepatientbloodgroup";
                break;
            case patientbussinessModel:
                url = BASE_URL + "patientbussinessModel";
                break;
            default:
                System.out.println("Google - biggest search giant.. ATT - my carrier provider..");
                break;
        }

        return url;
    }

    public JSONObject request_services() {
        Services service = new Services(context);
        JSONObject receiveData = null;
        switch (serviceName) {
            case LogIn:
                System.out.println("login.");
                receiveData = service.LogIn(sendData, BASE_URL1 + url_parts[1] + "LogIn");
                break;

            case SignUpByPatient:
                receiveData = service.common(sendData, BASE_URL + "SignUpByPatient");
                break;


            default:
                System.out.println("Google - biggest search giant.. ATT - my carrier provider..");
                break;
        }
        return receiveData;
    }

    public enum Services_static {
        LogIn, IsUserAuthenticated, GetCredentialDetails, agreeTermsCondition, SignUpPatient,
        LogOutIOS, ChangePasswordIOS, Register, GetUserDisclaimer, FacebookLinked, FacebookLoginMobile,
        FacebookUnLinked, GetUserDeatils, ForgotPassword, NeedHelp, CheckEmailIdIsExistMobile,
        PatientDisclaimer, EmailIdExistFacebook, GetUserCodeFromEmail, SignUpByPatient,
        GetPatientTestImagesInCase, PatientFileVault, UpdateImage, GetpatienttestReportAndroid,
        GetUserDetailsFromContactNoMobileService, GetPatientFiles, loadVaultMobile, getDistinctTags,
        DeletePatientFiles, GetPatientImagesInCase, GetAutoAreaSearch, checkAliasExist, GetLabByTest,
        FindLabsTest, GetPatientHistory, getBasicDetails, saveOtherDetail, GetOthersFromArea,
        GetAllVaccines, GetPatientAlertList, GetAdvisedInvestigationDetailMobile,
        GetAllLisPatientCaseDetailMobileNew, SendLabContactDetail, UpdateContact, SamplePickUp, BookTest,
        GetLabList, GetAllTestData, GetPhotographData, GetDoctorData, GetCentreData, GetPatient,
        GetpatienttestReport, GetAllCompletedTestDetailsOfPatient, UploadPrescriptionMail,
        SendCouponWithTestViaSMSNew, SendCouponWithOutTestViaSMS, SendCouponWithTestViaEmailNew,
        SendCouponWithOutTestViaEmail, GetPatientTestRangeDataMobile, GetPatientVerification,
        EmailVerificationClinic, ResendSmsVerifyCodeClinic, CheckVerifyCodeClinic, GetAllNationality,
        GetCountryList, GetStateList, GetCityList1, GetStateFromCity, GenerateCouponNo,
        PatientFileVaultNew, CheckEmailIdIsExist, CheckAliasExistMobile, IsUserNameAliasExists, GetPatientTestReportMobile,
        GetTestByLab, BookTestNew, SinglePackageDetails, GetOrderHistoryDetails, applypromocode,
        GetFilesForOrderID, IsContactNoExists, SendAllReportToUser, GenerateInvoice, CheckLabrangefrom_area,
        GetCityList, AllPackage, HomePackage, BASE_URL, LIVELOGIN_URL, CallmePatient, CancelOrder,
        CreateFolder, DeleteObject, MoveObject, deleteSingularDetails, saveHealthDetail, getAllergies,
        getNationality, saveBasicDetail, UploadProfilePic, GetAllObjectFromS3, CreateLockFolder,
        GetMember, AddMember, AcceptRequest, IsContactExist, GetMemberRecords, getpatientHistoryDetails,
        Updatepatientbloodgroup,patientbussinessModel
    }

}


