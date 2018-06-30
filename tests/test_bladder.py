from unittest import TestCase
from unittest.mock import Mock

from a_long_hour_and_a_half import Game
from a_long_hour_and_a_half.enums import FEMALE
from a_long_hour_and_a_half.bladder import Bladder


class TestBladder(TestCase):
    def setUp(self):
        self.game = Game()
        self.state = self.game.state
        self.bladder = self.game.state.character.bladder

    def test_initial_values(self):
        bladder = Bladder(self.state, FEMALE)

        self.assertLessEqual(bladder.urine, bladder.maximal_urine / 2)
        self.assertEqual(bladder.tummy_water, 0)
