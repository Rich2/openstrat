/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.geom

/** A pair of Seqs of display objects. Back objects are overlaid by front objects. The head of the back Seq is painted first. The last element
 *  of the front Seq is painted last. This class has proved useful as a temporary solution but I think it needs to be replaced with a solution 
 *  where the front -back display property is stored within the individual canvas elements. */
case class Disp2(backs: CanvObjs, fronts: CanvObjs)
{
   def ++(other: Disp2): Disp2 = new Disp2(backs ++ other.backs, fronts ++ other.fronts)
   def ++(other: (CanvObjs, CanvObjs)): Disp2 = Disp2(backs ++ other._1, fronts ++ other._2)
   def collapse: CanvObjs = backs ::: fronts
   def firstAppend(co: CanvO): Disp2 = Disp2(backs :+ co, fronts)
   def secondAppend(co: CanvO): Disp2 = Disp2(backs, fronts :+ co)
   def frontAppend(objs: CanvObjs): Disp2 = Disp2(backs, fronts ++ objs)
}

object Disp2
{
   def vp(backs: CanvO*)(fronts: CanvO*): Disp2 = new Disp2(backs.toList, fronts.toList)
   def empty: Disp2 = Disp2(Nil, Nil)
   def b1(back1: CanvO, fronts: CanvO *): Disp2 = Disp2(List(back1), fronts.toList)
  
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