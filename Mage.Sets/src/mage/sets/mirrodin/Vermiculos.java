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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author fireshoes
 */
public class Vermiculos extends CardImpl {
    
    private static final FilterPermanent filter = new FilterArtifactPermanent("an artifact");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public Vermiculos(UUID ownerId) {
        super(ownerId, 80, "Vermiculos", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Horror");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an artifact enters the battlefield, Vermiculos gets +4/+4 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new BoostSourceEffect(4, 4, Duration.EndOfTurn), filter, false));
    }

    public Vermiculos(final Vermiculos card) {
        super(card);
    }

    @java.lang.Override
    public Vermiculos copy() {
        return new Vermiculos(this);
    }
}
