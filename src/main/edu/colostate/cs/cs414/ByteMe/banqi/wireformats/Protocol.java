package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

public class Protocol {

	//list of messages, and their corresponding numbers
	final public static int SendRegistration = 2;
	final public static int RegistryReportsRegistrationStatus = 3;
	final public static int SendDeregistration= 4;
	final public static int RegistryReportsDeregistrationStatus = 5;
	final public static int LogIn = 6;
	final public static int RequestPassword = 7;
	final public static int NickNameDoesNotExist = 8;
	final public static int SendPassword = 9;
	final public static int SendUser = 11;
	final public static int SendLogOff = 34;
	final public static int SendInvite = 12;
	final public static int SendAccept = 13;
	final public static int StartGame = 15;
	
//	final public static int RegistrySendsNodeManifest = 6;
//	final public static int NodeReportsOverlaySetupStatus = 7;
//	final public static int RegistryRequestsTaskInitiate = 8;
//	final public static int OverlayNodeSendsData = 9;
//	final public static int OverlayNodeReportsTaskFinished = 10;
//	final public static int RegistryRequestsTrafficSummary = 11;
//	final public static int OverlayNodeReportsTrafficSummary = 12;
	
	
}
