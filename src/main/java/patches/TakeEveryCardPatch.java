package patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import ui.TakeAllButton;

import java.util.ArrayList;
import java.util.Iterator;

@SpireInitializer
public class TakeEveryCardPatch {
    private static final TakeAllButton takeAllButton = new TakeAllButton();

    @SpirePatch(
        clz = CardRewardScreen.class,
        method = "render"
    )
    public static class RenderTakeEveryCardFromCardReward {
        public static void Postfix(CardRewardScreen __instance, SpriteBatch sb, boolean ___draft, boolean ___discovery, boolean ___chooseOne, boolean ___codex) {
            if (!PeekButton.isPeeking && !___draft && !___discovery && !___chooseOne && !___codex) {
                takeAllButton.render(sb);
            }
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "update"
    )
    public static class UpdateTakeEveryCardFromCardReward {
        public static void Postfix(CardRewardScreen __instance) {
            if (!PeekButton.isPeeking) {
                takeAllButton.update();
            }
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "open"
    )
    public static class RemoveSkippable {
        public static void Postfix(CardRewardScreen __instance, ArrayList<AbstractCard> cards, RewardItem rItem, String header) {
            takeAllButton.rewardItem = rItem;
            takeAllButton.cards = cards;
            takeAllButton.show();
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "acquireCard"
    )
    public static class ReplaceAcquireCard {
        public static void Replace(CardRewardScreen __instance, AbstractCard hoveredCard) {
            // this function should do nothing now
        }
    }

    @SpirePatch(
            clz = CardRewardScreen.class,
            method = "takeReward"
    )
    public static class ReplaceTakeReward {
        public static void Replace(CardRewardScreen __instance, AbstractCard hoveredCard) {
            // this function should do nothing now
        }
    }
}
