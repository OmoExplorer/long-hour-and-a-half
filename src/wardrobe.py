from wear import UnderwearModel, OuterwearModel


class WearModelNotFoundException(Exception):
    def __init__(self, name):
        self.__name = name

    def __str__(self):
        return self.__name


underwear_models = (
    UnderwearModel('Strings', 'panties', 1, 2, 1),
    UnderwearModel('Tanga panties', 'panties', 1.5, 3, 1),
    UnderwearModel('Regular panties', 'panties', 2, 4, 1),
    UnderwearModel('Briefs', 'briefs', 4, 7, 1),

    UnderwearModel('Swimsuit', 'swimsuit', 4, 2.5, 2.5),
    UnderwearModel('String bikini', 'bikini panties', 1, 1, 2),
    UnderwearModel('Regular bikini', 'bikini panties', 2, 2, 2),

    UnderwearModel('Light diaper', 'diaper', 9, 50, 0),
    UnderwearModel('Normal diaper', 'diaper', 18, 100, 0),
    UnderwearModel('Heavy diaper', 'diaper', 25, 175, 0),

    UnderwearModel('Light pad', 'pad', 2, 16, 0.25),
    UnderwearModel('Normal pad', 'pad', 3, 24, 0.25),
    UnderwearModel('Big pad', 'pad', 4, 32, 0.25),

    UnderwearModel('Pants', 'pants', 2.5, 5, 1),
    UnderwearModel('Shorts-alike pants', 'pants', 3.75, 7.5, 1),

    UnderwearModel('Anti-gravity pants', 'pants', 0, 4, 1),
    UnderwearModel('Super-absorbing diaper', 'diaper', 18, 600, 0)
)

outerwear_models = (
    OuterwearModel('Long jeans', 'jeans', 7, 12, 1.2),
    OuterwearModel('Knee-length jeans', 'jeans', 6, 10, 1.2),
    OuterwearModel('Short jeans', 'shorts', 5, 8.5, 1.2),
    OuterwearModel('Very short jeans', 'shorts', 4, 7, 1.2),
    OuterwearModel('Long trousers', 'trousers', 9, 15.75, 1.4),
    OuterwearModel('Knee-length trousers', 'trousers', 8, 14, 1.4),
    OuterwearModel('Short trousers', 'shorts', 7, 12.25, 1.4),
    OuterwearModel('Very short trousers', 'shorts', 6, 10.5, 1.4),
    OuterwearModel('Long skirt', 'skirt', 5, 6, 1.7),
    OuterwearModel('Knee-length skirt', 'skirt', 4, 4.8, 1.7),
    OuterwearModel('Short skirt', 'skirt', 3, 3.6, 1.7),
    OuterwearModel('Mini skirt', 'skirt', 2, 2.4, 1.7),
    OuterwearModel('Micro skirt', 'skirt', 1, 1.2, 1.7),
    OuterwearModel('Long skirt and tights', 'skirt and tights', 6, 7.5, 1.6),
    OuterwearModel('Knee-length skirt and tights', 'skirt and tights', 5, 8.75, 1.6),
    OuterwearModel('Short skirt and tights', 'skirt and tights', 4, 7, 1.6),
    OuterwearModel('Mini skirt and tights', 'skirt and tights', 3, 5.25, 1.6),
    OuterwearModel('Micro skirt and tights', 'skirt and tights', 2, 3.5, 1.6),
    OuterwearModel('Leggings', 'leggings', 10, 11, 1.8),
    OuterwearModel('Short male jeans', 'jeans', 5, 8.5, 1.2),
    OuterwearModel('Normal male jeans', 'jeans', 7, 12, 1.2),
    OuterwearModel('Male trousers', 'trousers', 9, 15.75, 1.4)
)


def get_underwear_model(name):
    for model in underwear_models:
        if model.name == name:
            return model

    raise WearModelNotFoundException(name)


def get_outerwear_model(name):
    for model in outerwear_models:
        if model.name == name:
            return model

    raise WearModelNotFoundException(name)
