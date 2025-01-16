package org.craftarix.monitoring;

import com.google.common.collect.Lists;
import lombok.var;
import org.bukkit.entity.Player;
import org.craftarix.monitoring.config.Settings;
import org.craftarix.monitoring.menu.impl.PaginatedMenu;
import org.craftarix.monitoring.util.ItemUtil;

public class VoteMenu extends PaginatedMenu {

    private static final Settings settings = MonitoringPlugin.INSTANCE.getSettings();

    public VoteMenu() {
        super(settings.getInventoryTitle(), 54);

        setMarkupList(Lists.newArrayList(settings.getProductSlots()));

        setNextIcon(settings.getNextPage());
        setPrevIcon(settings.getPrevPage());
    }

    @Override
    protected void drawInventory(Player player) {
        var voteService = MonitoringPlugin.INSTANCE.getVoteService();
        var votes = voteService.getVotes(player.getName());

        settings.getDesign().forEach(item -> {
            var newIcon = ItemUtil.replace(item.getIcon(), "{votes}", String.valueOf(votes));
            var newItem = item.clone();
            newItem.setIcon(newIcon);
            draw(newItem);
        });

        settings.getProducts().forEach(product -> {
            var newIcon = ItemUtil.replace(product.getIcon(), "{votes}", String.valueOf(votes));
            addToMarkup(newIcon, (event) -> {
                var currentVotes = voteService.getVotes(player.getName());
                if (currentVotes < product.getPrice()) {
                    replaceItem(event.getSlot(), settings.getNotEnoughVotesIcon(), 40);
                    settings.getNotEnoughVotesMessages().forEach(message -> {
                        player.sendMessage(message.replace("{player}", player.getName())
                                .replace("{votes}", String.valueOf(currentVotes)));
                    });
                } else {
                    product.executeCommands(player);
                    voteService.takeVote(player.getName(), product.getPrice());
                    updateInventory(player);
                    replaceItem(event.getSlot(), settings.getSuccessBuyIcon(), 40);
                }
            });
        });
    }
}
