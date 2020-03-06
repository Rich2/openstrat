/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags
import geom._, Colour._

trait Flag
{ def apply(): GraphicElemsOld
  def name: String
   
  /** Equal width vertical bands. width ratio should normally be greater than 1.0 */
  def leftToRight(ratio: Double, colours: Colour*): GraphicElemsOld = colours.iMap((colour, i) => Rectangle.fromTL(ratio / colours.length, 1,
    -ratio / 2 vv + 0.5).slate(i * ratio / colours.length, 0).fill(colour))
         
   /** Equal height horrisontal bands. width ratio should normally be greater than 1.0 */
   def topToBottom(retObj: AnyRef,ratio: Double, colours: Colour*): PolySubj = Rectangle(ratio, 1).subjSeq(retObj,
     colours.iMap((colour, i) => Rectangle.fromTL(ratio, 1.0 / colours.length, -ratio / 2 vv + 0.5).slate(0,
       - i.toDouble / colours.length).fill(colour)))
}

object Flag
{

}

object Armenia extends Flag
{ def apply = leftToRight(2, Red, Blue, Gold)
  def name = "Armenia"
}