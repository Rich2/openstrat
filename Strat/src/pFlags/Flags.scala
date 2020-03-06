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
  //)Rectangle(ratio, 1).subjSeq(retObj,
}

object Flag
{

}

object Armenia extends Flag
{ def ratio = 2
  def apply: Refs[PaintElem] = leftToRight(Red, Blue, Gold)
  def name = "Armenia"
}

object Austria extends Flag
{ val ratio = 1.5
  def apply: Refs[PaintElem] = topToBottom(Black, Yellow)
  def name = "Austria"
}

