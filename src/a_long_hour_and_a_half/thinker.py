from random import choice

from termcolor import colored

from .enums import DayState


class Thinker:
    """Components for showing thoughts: a convenient way to show the current character state."""

    def __init__(self, day):
        self._day = day

    def require_thought(self, *thoughts, color=None, style=None):
        if color is None:
            self._day.character.thoughts += '\n' + choice(thoughts)
        else:
            self._day.character.thoughts += '\n' + colored(choice(thoughts), color, attrs=style)

    def think_about_bladder_fullness(self):
        urine_ratio = self._day.character.bladder.urine_decimal_ratio

        if urine_ratio < 0.2 and self._day.state == DayState.LESSON:
            if self._day.teacher.testing:
                self.require_thought("What the hell is this test?",
                                     'This test is ridiculous.',
                                     'Hmm... Maybe A)... no, D)... I dunno.')
            else:
                self.require_thought(self._day.current_lesson() + ' is so boring!',
                                     "How much time is it? I can't wait for the lesson to finish!",
                                     'Bla-bla-bla... the teacher is so boring!')

        elif 0.2 < urine_ratio < 0.35 and self._day.state == DayState.LESSON:
            if self._day.teacher.testing:
                self.require_thought("What the hell is this test?",
                                     'This test is ridiculous.',
                                     'Hmm... Maybe A)... no, D)... I dunno.')
            else:
                self.require_thought(self._day.current_lesson() + ' is so boring!',
                                     "I've got to pee a bit, but... Not really much.",
                                     "How much time is it? I can't wait for the lesson to finish!")

        elif 0.35 < urine_ratio < 0.5:
            if self._day.state == DayState.LESSON:
                if self._day.teacher.testing:
                    self.require_thought(
                        "What the hell is this test? That slight peeing urges ain't helping.",
                        'This test is ridiculous. And I have to pee a bit.',
                        'Hmm... Maybe A)... no, D)... I dunno.')
                else:
                    self.require_thought(
                        self._day.current_lesson() + ' is so boring! Also I need to pee.',
                        "I've got to pee a bit, but... Not really much.",
                        "When this lesson will be over? I've got to pee.")
            else:
                self.require_thought('I need to pee slightly.',
                                     'I need to pee. Maybe use a restroom?',
                                     "I've got to pee a bit, but... Not really much.",
                                     "I've got to pee.")

        elif 0.5 < urine_ratio < 0.65:
            self.require_thought('Ugh... I need to pee quite badly.',
                                 "I've got to pee! I'm somehow able to deal with it... "
                                 "at least for now...",
                                 "Damn, I've got to pee. That's annoying!")

        elif 0.65 < urine_ratio < 0.8:
            self.require_thought('Argh! I need to pee badly!',
                                 'I need to pee pretty badly.',
                                 "I've got to pee! And this is a problem now!",
                                 "Damn, I've got to pee really badly!",
                                 color='yellow')

        elif 0.8 < urine_ratio < 1:
            self.require_thought("Damn! I've got to pee REALLY badly!!!",
                                 "I need to pee NOW!",
                                 "I need to pee right NOW!",
                                 "Need to pee, need to pee, need to pee...",
                                 "Uhh! I've gotta go very badly! "
                                 "I don't know whether I'm able to hold it!!",
                                 color='yellow')

    def think_about_low_sphincter_power(self):
        if self._day.character.sphincter.power < 10:
            self.require_thought("Oops... It's coming!",
                                 "It's gotta come out!!!",
                                 "Pee is coming!",
                                 "Ouch! I can't hold it!")

    def think_about_toilet_queue(self):
        self.require_thought('There is a big queue for toilets.',
                             'Damn! There is a big queue for toilets.',
                             'There is a lot of people there. All cabins are occupied.',
                             'All cabins are occupied. Damn, I have to wait.',
                             color='yellow')

    def think_about_closed_toiled(self):
        self.require_thought('No! Toilets are closed!',
                             'Damn, damn, damn! Toilets are out of order!',
                             'Shit! Toilets are locked for all day!',
                             'Uh oh. Toilets are out of order for all day.',
                             'There is a note on the door: "Sorry! Out of order". Are they kidding?',
                             'Are they kidding?! "Out of order"!',
                             color='red')

    def think_about_embarrassment(self):
        self.require_thought("Oh... I'm so embarrassed...",
                             'I feel embarrassed...',
                             "Classmates know that I have to pee. That's very bad.")

    def think_about_thirst(self):
        self.require_thought("I'm thirsty.",
                             "I'm so thirsty...",
                             "I want to drink.",
                             "I need to drink.")

    def think_about_test(self):
        self.require_thought('What? Teacher is giving us some test!',
                             'Oops... Looks like we will write a test now.',
                             color='yellow')

    def think_about_toilet_denial(self):
        self.require_thought('Teacher has denied to go out.',
                             "I wasn't allowed to go out.",
                             color='yellow')

    def think_about_toilet_denial_during_test(self):
        self.require_thought("Teacher has denied to go out, because we're writing a test now.",
                             "I'm not allowed to go out during a test.",
                             color='yellow')

    def think_about_toilet_approval(self):
        self.require_thought('Yeah! I was allowed to go out!',
                             'Yes! I can go out!',
                             color='green')

    def think_about_peeing(self):
        self.require_thought('Ahhhh... Sweet relief...',
                             'Oh yeah... I finally peed!',
                             'Yeahhhh... I was waiting for this for long.',
                             'Ahhhh... Finally...',
                             color='green')

    def think_about_inability_to_stop_peeing(self):
        self.require_thought("Aaaaaah!!! I can't stop!",
                             "Aaaaaah!!! I can't stop peeing!",
                             "Aaaaaah!!! I can't stop the flow!",
                             "Aaaaaah!!! I can't stop it!",
                             color='red')

    def think_about_peeing_more_than_intended(self):
        self.require_thought("Damn! I peed a bit much than I was going to.",
                             "Uhhh! I hardly stopped peeing!",
                             "Uhhh! I hardly stopped the flow!",
                             "Ohhh... It was hard to stop.",
                             color='yellow')

    def think_about_inability_to_start_peeing(self):
        self.require_thought("I can't start peeing.",
                             "I'm trying to pee, but I can't.",
                             "I can't force myself to pee right here and now.",
                             color='yellow')

    def think_about_leaking(self):
        self.require_thought("Oops! I'm peeing!",
                             "No, no, NO! I'm peeing!!!",
                             "Uh-oh... I have leaked...",
                             "Oops! I have leaked!",
                             "Damn, I can't help it, I'm leaking!!",
                             "Oh no, I have leaked! I won't last much time!",
                             "I barely can hold it... Ooooh, I feel wet...",
                             color='red', style=['bold'])

    def think_about_staying_after_classes(self):
        self.require_thought('Oh no... I was told to stay after classes!',
                             'Damn! I will have to stay after classes!',
                             color='red')

    def think_about_staying_on_next_break(self):
        self.require_thought('Oh no... I was told to stay on the next break!',
                             'Damn! I will have to stay on the next break!',
                             color='red')

    def think_about_teacher_question(self):
        self.require_thought("Oh! Teacher asked me to answer a question. I don't know what to answer.",
                             color='yellow')
