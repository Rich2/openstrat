/* Copyright 2018-25 Richard Oliver, w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pFlags
import geom.*, Colour.*

object Austria extends Flag
{ def name = "Austria"
  val ratio = 1.5
  def apply(): GraphicElems = topToBottom(Black, Yellow)
}

object Belgium extends Flag
{ val name = "Belgium"
  val ratio = 15.0 / 13.0
  def apply(): GraphicElems = leftToRight(Black, Yellow, Red)
}

trait EnglandLike extends Flag
{ def ratio: Double = 5.0 / 3.0
  def englishRed: Colour = Colour.fromInts(204, 0, 0)
  def redCross: RArr[PolygonFill] = Rect.cross(ratio, 1, 0.2).map(_.fill(englishRed))
  def common = rect.fill(White) %: redCross
}

object England extends EnglandLike
{ val name = "England"
  def apply(): GraphicElems = common
}

object UnitedKingdom extends EnglandLike
{ val name = "United Kingdom"

  def apply(): GraphicElems =
  { val xd = hypotenuse(ratio, 1) / 30.0
    val yd = hypotenuse(1, 0.5) / 30.0

    /** Top of White crossbar */
    val ywc = 5.0 / 30

    /** ywc * 2 where diag crosses ywc */
    val xDiag = 10.0 / 30.0

    val b1 = Polygon.dbls(
      5.0 / 30, 0.5, 1 - xd * 3, 0.5,
      1.0 / 6.0, ywc + yd)

    val b2 = Polygon.dbls(
      xDiag + 3 * xd, ywc,
      1, 0.5 - yd * 3,
      1, ywc)

    val r1: Polygon = Polygon.dbls(
      -1, 0.5,
      -xDiag, ywc,
      -(xDiag + xd * 2), ywc,
      -1, 0.5 - (yd * 2))

    val r2: Polygon = Polygon.dbls(
      xDiag - xd * 2, ywc,
      1 - xd * 2, 0.5,
      1, 0.5,
      xDiag, ywc)

    val reds1 = RArr(r1, r2).map(_.fill(englishRed))
    val reds = reds1.flatMap(e => RArr(e, e.negY.negX))

    val blues = {
      val l1 = RArr(b1, b2).map(_.fill(Colour.fromInts(0, 0, 102)))
      l1.flatMap(b => RArr(b, b.negY, b.negX, b.negY.negX))
    }
    common ++ blues ++ reds
  }
}

object France extends Flag
{ val name = "France"
  val ratio = 1.5
  def apply(): GraphicElems = leftToRight(Colour(0xFF0055A4) , White, Colour(0xFFEF4135))
}

object Germany extends Flag
{ val name = "Germany"
  val ratio = 5 / 3.0
  def apply(): GraphicElems = topToBottom(Black, Red, Gold)
}

object Germany1871 extends Flag
{ val name = "Germany (1871)"
  val ratio = 1.5
  def apply(): GraphicElems = topToBottom(Black, White, Red)
}

object Italy extends Flag
{ val name = "Italy"
  val ratio = 1.5
  def apply(): GraphicElems = topToBottom(Green, White, Red)
}

object Ireland extends Flag
{ val name = "Ireland"
  val ratio = 2
  def apply(): GraphicElems = topToBottom(Green, White, Orange)
}

object Russia extends Flag
{ val ratio = 1.5
  val name = "Russia"
  def apply(): GraphicElems = topToBottom(White, Blue, Red)
}

object USSR extends Flag
{ val name = "USSR"
  val ratio = 1.5
  def apply(): GraphicElems = RArr(
    Rect(ratio, 1).fill(Red),
    Star5().scale(0.4).fill(Gold)
  )
}

object Swastika extends Flag
{ val name = "Swastika"
  val ratio = 5 / 3.0
  def apply(): GraphicElems =
  { val poly = Rect(ratio, 1)
    val bar = Rect.bCen(0.1, 0.2).fill(Black)
    val arm = Rect.tl(6.0 / 20, 0.1, -1.0 / 20, 0.25).fill(Black)
    val cross = RArr(bar, arm).rotate45.flatMap(_.rotateQuadrants)
    RArr(poly.fill(Red), Circle.d(6.0 / 8).fill(White)) ++ cross
  }
}

object CzechRepublic extends Flag
{ val name = "Czech Republic"
  val ratio = 1.5
  def apply(): GraphicElems = RArr(
    Rect(ratio, 1).fill(White),
    Rect(ratio, 0.5).slate(0, -0.25).fill(Colour(0xFFD7141A)),
    Triangle(-ratio / 2, 0.5, -ratio / 2, -0.5, 0, 0).fill(Colour(0xFF11457E))
  )
}

object CCCP extends Flag
{ val name = "CCCP"
  val ratio = 2.0
  def apply(): GraphicElems = RArr(
    //background
    Rect(ratio, 1).fill(Colour(0xFFCC0000)),
    //hammer
    ShapeGenOld(LineTail(-0.7709, 0.2138), LineTail(-0.7395, 0.1822), LineTail(-0.7099, 0.2116),
      BezierTail(-0.6648, 0.1633, -0.6175, 0.1166, -0.5727, 0.06808), BezierTail(-0.566, 0.06131, -0.555, 0.06128, -0.5483, 0.068),
      BezierTail(-0.5415, 0.07472, -0.5415, 0.08566, -0.5482, 0.09243), BezierTail(-0.5962, 0.1378, -0.6444, 0.1834, -0.6924, 0.2289),
      LineTail(-0.6525, 0.2686), LineTail(-0.7081, 0.2763), LineTail(-0.7709, 0.2138)).fill(Colour(0xFFFFD700)),
    //sickle
    ShapeGenOld(LineTail(-0.6695, 0.3163), BezierTail(-0.6437, 0.3018, -0.624, 0.2809, -0.6124, 0.259),
      BezierTail(-0.6007, 0.2369, -0.5955, 0.2137, -0.5954, 0.1953), BezierTail(-0.5952, 0.1574, -0.6262, 0.1266, -0.6641, 0.1266),
      BezierTail(-0.6843, 0.1266, -0.7025, 0.1354, -0.715, 0.1493), LineTail(-0.722, 0.1434),
      BezierTail(-0.7232, 0.1439, -0.7244, 0.1441, -0.7257, 0.1441), BezierTail(-0.7287, 0.1441, -0.7316, 0.1428, -0.7336, 0.1405),
      BezierTail(-0.7386, 0.1398, -0.7427, 0.1364, -0.7443, 0.1316), BezierTail(-0.7495, 0.1212, -0.7587, 0.1129, -0.7698, 0.1092),
      BezierTail(-0.7699, 0.1091, -0.77, 0.1091, -0.7701, 0.109), BezierTail(-0.7752, 0.1072, -0.7803, 0.1038, -0.7847, 0.09938),
      BezierTail(-0.7934, 0.09065, -0.7976, 0.07948, -0.7952, 0.07165), BezierTail(-0.7954, 0.07097, -0.7956, 0.07025, -0.7956, 0.06953),
      BezierTail(-0.7956, 0.06591, -0.7926, 0.06298, -0.789, 0.06298), BezierTail(-0.7881, 0.06298, -0.7872, 0.06317, -0.7864, 0.06354),
      BezierTail(-0.7786, 0.06178, -0.7679, 0.06612, -0.7596, 0.07447), BezierTail(-0.7549, 0.07922, -0.7514, 0.08485, -0.7497, 0.09036),
      BezierTail(-0.7458, 0.1014, -0.7375, 0.1104, -0.727, 0.1154), BezierTail(-0.7267, 0.1156, -0.7265, 0.1157, -0.7263, 0.1158),
      BezierTail(-0.7228, 0.1175, -0.7202, 0.1207, -0.7193, 0.1245), BezierTail(-0.7031, 0.1054, -0.6789, 0.09325, -0.6517, 0.09262),
      BezierTail(-0.602, 0.09145, -0.5649, 0.128, -0.5631, 0.1807), BezierTail(-0.5623, 0.2072, -0.5725, 0.2413, -0.5959, 0.2693),
      BezierTail(-0.6137, 0.2907, -0.6399, 0.3085, -0.6695, 0.3163), LineTail(-0.6695, 0.3163)).fill(Colour(0xFFFFD700)),
    //outer star
    Star5().scale(1.0/16).slate(-2.0/3, 0.75/2).fill(Colour(0xFFFFD700)),
    //inner star
    Star5().scale(1.0/25).slate(-2.0/3, 0.75/2).fill(Colour(0xFFCC0000)),
  )
}
