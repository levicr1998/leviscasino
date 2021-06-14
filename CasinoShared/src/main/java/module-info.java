module websocketshared {
    requires gson;
    exports org.cri.levi.websocketcasinoshared.models;
    exports org.cri.levi.websocketcasinoshared.models.gamemodels;
    exports org.cri.levi.websocketcasinoshared.models.loginmodels;
    exports org.cri.levi.websocketcasinoshared.models.bankmodels;
    exports org.cri.levi.websocketcasinoshared.models.lobbymodels;


    opens org.cri.levi.websocketcasinoshared.models;
}