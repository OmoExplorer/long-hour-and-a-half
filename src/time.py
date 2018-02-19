class Time:
    def __init__(self, hours, minutes):
        self.hours = hours
        self.minutes = minutes

    def __warp_time(self):
        if self.hours > 23:
            self.hours -= 24
        elif self.hours < 0:
            self.hours += 24

        if self.minutes > 59:
            self.minutes -= 60
            self.hours += 1
        elif self.minutes < 0:
            self.minutes += 60
            self.hours -= 1

    def __add__(self, other):
        self.hours += other.hours
        self.minutes += other.minutes
        self.__warp_time()

    def __sub__(self, other):
        self.hours -= other.hours
        self.minutes -= other.minutes
        self.__warp_time()
