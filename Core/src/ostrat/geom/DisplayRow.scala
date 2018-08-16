/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This is not well documented */
case class DisplayRow(margin: Double, subjs: List[CanvSubj[_]])
{
   def fromLeft(leftPt: Vec2): List[CanvSubj[_]] =
   {      
      def loop(rem: Seq[CanvSubj[_]], acc: List[CanvSubj[_]], x: Double): List[CanvSubj[_]] = rem.fHead(
            acc,
            (head, tail) =>
               loop(tail,
                     acc :+
                     head.slateX(
                           {
                              if (head == null) println("DiplayRow null " + rem.length)
                           x + head.width / 2}).
                        asInstanceOf[CanvSubj[_]],
                     x + head.width + margin)
            )
      loop(subjs, Nil, leftPt.x + margin)
   }
  
}