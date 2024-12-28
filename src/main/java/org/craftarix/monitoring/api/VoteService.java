package org.craftarix.monitoring.api;

import org.craftarix.monitoring.api.model.CurrentServerModel;

public interface VoteService {

    int getVotes(String playerName);

    void takeVote(String playerName, int votes);

    CurrentServerModel info();


}
