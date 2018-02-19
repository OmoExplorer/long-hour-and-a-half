class _WearModel:
    def __init__(self, name, in_game_name, pressure, absorption, drying):
        self.name = name
        self.in_game_name = in_game_name if in_game_name is not None else name
        self.pressure = pressure
        self.absorption = absorption
        self.drying = drying


class UnderwearModel(_WearModel):
    pass


class OuterwearModel(_WearModel):
    pass


class _Wear:
    def __init__(self, model, color):
        self.name = model.name
        self.in_game_name = model.in_game_name
        self.pressure = model.pressure
        self.absorption = self.dryness = model.absorption
        self.drying = model.drying
        self.color = color

    def __str__(self):
        return self.name


class Underwear(_Wear):
    pass


class Outerwear(_Wear):
    pass
