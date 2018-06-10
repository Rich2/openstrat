/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom

trait CanvObj[T] extends Transable[T]

/** A pair of Seqs of display objects. Back objects are overlaid by front objects. The head of the back Seq is painted first. The last
 *  element of the front Seq is painted last */
case class Disp2(backs: CanvObjs, fronts: CanvObjs)
{
   def ++(other: Disp2): Disp2 = new Disp2(backs ++ other.backs, fronts ++ other.fronts)
   def ++(other: (CanvObjs, CanvObjs)): Disp2 = Disp2(backs ++ other._1, fronts ++ other._2)
   def collapse: Seq[CanvObj[_]] = backs ++ fronts
   def firstAppend(co: CanvO): Disp2 = Disp2(backs :+ co, fronts)
   def secondAppend(co: CanvO): Disp2 = Disp2(backs, fronts :+ co)
   def frontAppend(objs: CanvObjs): Disp2 = Disp2(backs, fronts ++ objs)
}

object Disp2
{
   def vp(backs: CanvO*)(fronts: CanvO*): Disp2 = new Disp2(backs, fronts)
   def empty: Disp2 = Disp2(Seq(), Seq())
   def b1(back1: CanvO, fronts: CanvO *): Disp2 = Disp2(Seq(back1), fronts)
  // def backsOnly(back: CanvObjs): Disp2 = Disp2(back, Seq())
   //def back1front1(backElem: CanvO, frontElem: CanvO): Disp2 = Disp2(Seq(backElem), Seq(frontElem))
 //  def back1fronts(backElem: CanvO, frontSeq: CanvObjs): Disp2 = Disp2(Seq(backElem), frontSeq)
   implicit class Disp2SeqImplicit(thisSeq: Seq[Disp2])
   {
      def displayFlatten: Disp2 = thisSeq.foldLeft(Disp2.empty)(_ ++ _)
      def displayCollapse: CanvObjs = displayFlatten.collapse
   }
   implicit class SeqToDisp2Implicit[A](thisSeq: Seq[A])
   {
      def flatMaptoDisp2(f: A => (CanvObjs, CanvObjs)): Disp2 = ???
   }
}

trait ClickEl[T] extends CanvObj[T]
{
   def retObj: AnyRef
   def ptIn: Vec2 => Boolean
}
case class ClickPoly(verts: Vec2s, retObj: AnyRef) extends ClickEl[ClickPoly]
{
   override def ptIn: Vec2 => Boolean = verts.ptInPolygon
   override def fTrans(f: Vec2 => Vec2) = ClickPoly(verts.fTrans(f), retObj)
}

case class ClickShape(shape: Seq[ShapeSeg], retObj: AnyRef) extends ClickEl[ClickShape]
{
   override def ptIn: Vec2 => Boolean = shape.ptInShape
   override def fTrans(f: Vec2 => Vec2) = ClickShape(shape.fTrans(f), retObj)
}

