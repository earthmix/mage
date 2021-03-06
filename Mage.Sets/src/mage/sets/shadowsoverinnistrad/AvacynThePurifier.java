/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.shadowsoverinnistrad;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class AvacynThePurifier extends CardImpl {

    public AvacynThePurifier(UUID ownerId) {
        super(ownerId, 5, "Avacyn, the Purifier", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "");
        this.expansionSetCode = "SOI";
        this.supertype.add("Legendary");
        this.subtype.add("Angel");
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature transforms into Avacyn, the Purifier, it deals 3 damage to each other creature and each opponent.
        this.addAbility(new AvacynThePurifierAbility());
    }

    public AvacynThePurifier(final AvacynThePurifier card) {
        super(card);
    }

    @Override
    public AvacynThePurifier copy() {
        return new AvacynThePurifier(this);
    }
}

class AvacynThePurifierAbility extends TriggeredAbilityImpl {

    public AvacynThePurifierAbility() {
        super(Zone.BATTLEFIELD, new AvacynThePurifierEffect(), false);
    }

    public AvacynThePurifierAbility(final AvacynThePurifierAbility ability) {
        super(ability);
    }

    @Override
    public AvacynThePurifierAbility copy() {
        return new AvacynThePurifierAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        Permanent currentSourceObject = (Permanent) getSourceObjectIfItStillExists(game);
        if (currentSourceObject != null && currentSourceObject.isNightCard()) {
            return true;
        }
        return super.isInUseableZone(game, source, event);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.isTransformed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature transforms into Avacyn, the Purifier, it deals 3 damage to each other creature and each opponent.";
    }
}

class AvacynThePurifierEffect extends OneShotEffect {

    public AvacynThePurifierEffect() {
        super(Outcome.Damage);
    }

    public AvacynThePurifierEffect(final AvacynThePurifierEffect effect) {
        super(effect);
    }

    @Override
    public AvacynThePurifierEffect copy() {
        return new AvacynThePurifierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature");
        filter.add(new AnotherPredicate());
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent permanent : permanents) {
            permanent.damage(3, source.getSourceId(), game, false, true);
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.damage(3, source.getSourceId(), game, false, true);
            }
        }
        return true;
    }
}
