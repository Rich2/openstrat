/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags
import geom._, Colour._

trait Flag
{ def apply(): Refs[PaintElem]
  def name: String
  def ratio: Double
  def subj: PolySubj = Rectangle(ratio).subjSeq(name + " flag", apply)

  /** Equal width vertical bands. width ratio should normally be greater than 1.0 */
  def leftToRight(colours: Colour*): Refs[PaintElem] = colours.iMap((colour, i) => Rectangle.fromTL(ratio / colours.length, 1,
    -ratio / 2 vv + 0.5).slate(i * ratio / colours.length, 0).fill(colour))
         
   /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
   def topToBottom(colours: Colour*): Refs[PaintElem] = colours.iMap((colour, i) => Rectangle.fromTL(ratio,
     1.0 / colours.length, -ratio / 2 vv + 0.5).slate(0,
       - i.toDouble / colours.length).fill(colour))
}

object Armenia extends Flag
{ val name = "Armenia"
  val ratio = 2
  val apply: Refs[PaintElem] = leftToRight(Red, Blue, Gold)
}

object Austria extends Flag
{ def name = "Austria"
  val ratio = 1.5
  val apply: Refs[PaintElem] = topToBottom(Black, Yellow)
}

object Japan extends Flag
{ val name = "Japan"
  val ratio = 1.5
  val apply: Refs[PaintElem] =
  { val rect = Rectangle(ratio).fill(White)
    val circ = Circle.segs(0.6).fill(Colour.fromInts(188, 0,45))
    Refs(rect, circ)
  }
}

object Swastika extends Flag
{ val name = "Swastika"
  val ratio = 5 / 3.0
  val apply: Refs[PaintElem] =
  { val poly = Rectangle(ratio, 1)
    val bar = Rectangle.fromBC(0.1, 0.2).fill(Black)
    val arm = Rectangle.fromTL(6.0 / 20, 0.1, -1.0 / 20 vv 0.25).fill(Black)
    val cross = Refs(bar, arm).anti45.flatMap(_.rCrossArr)
    Refs[PaintElem](
      poly.fill(Red),
      Circle.segs(6.0 / 8).fill(White)
    ) ++ cross
  }
}