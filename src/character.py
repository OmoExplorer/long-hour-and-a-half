from bladder import Bladder


class CharacterProfile:
    def __init__(self, name, gender, incontinence):
        self.name = name
        self.gender = gender
        self.incontinence = incontinence


class Character:
    def __init__(self, profile, underwear, outerwear):
        self.name = profile.name
        self.gender = profile.gender
        self.bladder = Bladder(profile.incontinence)
        self.embarrassment = 0
        self.tummy_water = 0
        self.underwear = underwear
        self.outerwear = outerwear

    @property
    def dryness(self):
        return self.underwear.dryness + self.outerwear.dryness
