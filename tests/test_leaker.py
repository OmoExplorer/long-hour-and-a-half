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

    def test_minimal_leak_volume(self):
        for i in range(int(self.bladder.maximal_urine)):
            self.bladder.urine = i
            self.assertEqual(self.leaker.minimal_leak_volume,
                             max(1, self.bladder.urine ** 2 // 35000 - 23))

    def test_maximal_leak_volume(self):
        for i in range(int(self.bladder.maximal_urine)):
            self.bladder.urine = i
            self.assertEqual(self.leaker.maximal_leak_volume,
                             max(0, self.bladder.urine ** 2 // 20000 - 32))

    def test_leak_volume(self):
        for i in range(int(self.bladder.maximal_urine)):
            # with self.subTest():
                self.bladder.urine = i
                self.assertIn(self.leaker.get_leak_volume(),
                              range(self.leaker.minimal_leak_volume, self.leaker.maximal_leak_volume + 2))
