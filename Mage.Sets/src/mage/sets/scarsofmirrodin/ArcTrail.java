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

package mage.sets.scarsofmirrodin;

import java.io.ObjectStreamException;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureOrPlayer;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class ArcTrail extends CardImpl {

    public ArcTrail (UUID ownerId) {
        super(ownerId, 81, "Arc Trail", Rarity.UNCOMMON, new CardType[] { CardType.SORCERY }, "{1}{R}");
        this.expansionSetCode = "SOM";

        // Arc Trail deals 2 damage to target creature or player and 1 damage to another target creature or player
        FilterCreatureOrPlayer filter1 = new FilterCreatureOrPlayer("creature or player to deal 2 damage");
        TargetCreatureOrPlayer target1 = new TargetCreatureOrPlayer(1, 1, filter1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1);
        
        FilterCreatureOrPlayer filter2 = new FilterCreatureOrPlayer("another creature or player to deal 1 damage");
        AnotherTargetPredicate predicate = new AnotherTargetPredicate(2);
        filter2.getCreatureFilter().add(predicate);
        filter2.getPlayerFilter().add(predicate);
        TargetCreatureOrPlayer target2 = new TargetCreatureOrPlayer(1, 1, filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
        
        this.getSpellAbility().addEffect(ArcTrailEffect.getInstance());
    }

    public ArcTrail (final ArcTrail card) {
        super(card);
    }

    @Override
    public ArcTrail copy() {
        return new ArcTrail(this);
    }

}

class ArcTrailEffect extends OneShotEffect {

    private static final ArcTrailEffect fINSTANCE =  new ArcTrailEffect();

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static ArcTrailEffect getInstance() {
        return fINSTANCE;
    }

    private ArcTrailEffect ( ) {
        super(Outcome.Damage);
        staticText = "{source} deals 2 damage to target creature or player and 1 damage to another target creature or player";
    }

    @Override
    public boolean apply(Game game, Ability source) {

        boolean applied = false;
        boolean twoDamageDone = false;
        int damage = 2;

        for ( Target target : source.getTargets() ) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());

            if ( twoDamageDone ) {
                damage = 1;
            }

            if (permanent != null) {
                applied |= (permanent.damage( damage, source.getSourceId(), game, false, true ) > 0);
            }
            Player player = game.getPlayer(target.getFirstTarget());
            if (player != null) {
                applied |= (player.damage( damage, source.getSourceId(), game, false, true ) > 0);
            }

            twoDamageDone = true;
        }
        return applied;
    }

    @Override
    public Effect copy() {
        return fINSTANCE;
    }

}
