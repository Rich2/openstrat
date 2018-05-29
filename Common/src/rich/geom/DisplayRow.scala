/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom

/** This is not well documented */
case class DisplayRow(margin: Double, subjs: Seq[CanvSubj[_]])
{
   def fromLeft(leftPt: Vec2): Seq[CanvSubj[_]] =
   {      
      def loop(rem: Seq[CanvSubj[_]], acc: Seq[CanvSubj[_]], x: Double): Seq[CanvSubj[_]] = rem.fHead(
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