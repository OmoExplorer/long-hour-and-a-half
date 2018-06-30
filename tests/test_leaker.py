import unittest

from a_long_hour_and_a_half import Game


class TestLeaker(unittest.TestCase):
    def setUp(self):
        self.game = Game()
        self.state = self.game.state
        self.bladder = self.state.character.bladder
        self.leaker = self.state.character.leaker

    def test_leak_chance(self):
        for i in range(int(self.bladder.maximal_urine)):
            self.bladder.urine = i
            self.assertEqual(self.leaker.leak_chance, (self.bladder.urine - self.leaker._fullness_threshold) / 2)
