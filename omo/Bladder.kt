package omo

import java.io.Serializable

class Bladder(var incontinence: Double) : Serializable {
    var maximalSphincterStrength = 100 / incontinence
    var maximalFullness = 130.0
}