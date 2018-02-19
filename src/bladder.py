class Bladder:
    MAXIMAL_URINE = 1500

    def __init__(self, incontinence):
        self.urine = 0
        self.sphincter = Sphincter(incontinence)


class Sphincter:
    def __init__(self, incontinence):
        self.maximal_strength = 100 / incontinence
        self.strength = self.maximal_strength
        self.incontinence = incontinence
