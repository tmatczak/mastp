package sample;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class AgentsEnvironmentManager {

    private jade.wrapper.AgentContainer mainContainer;

    public void startContainer() throws Exception { // only main for a while
        Runtime rt = Runtime.instance();
        rt.setCloseVM(true);

        Profile profile = new ProfileImpl(null, 1200, null);
        mainContainer = rt.createMainContainer(profile);

//        ProfileImpl pContainer = new ProfileImpl(null, 1200, null);
//
//        jade.wrapper.AgentContainer cont = rt.createAgentContainer(pContainer);
    }

    public void addRemoteMonitoringAgent() {
        addAgentToMainContainer("rma", "jade.tools.rma.rma", new Object[0]);
    }

    public void addAgentToMainContainer(String nickname, String className, Object[] arguments) {
        addAgent(nickname, className, mainContainer, arguments);
    }

    public void addAgent(String nickname, String className, jade.wrapper.AgentContainer container, Object[] arguments) {
        try {
            AgentController agent = container.createNewAgent(nickname, className, arguments);
            agent.start();
        } catch (StaleProxyException e) {
            System.out.println("Agent not created due to stale proxy exception");
        }

    }
}
