/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._

object Flags
{
   def lToR(retObj: AnyRef, ratio: Double, colours: Colour*): PolySubj =  Rect.h1(ratio).subjSeq(evObj = retObj,
         colours.iMap((c, i) => FillPoly(Rect.tL( -ratio / 2, + 0.5, ratio / colours.length, 1).slate(i * ratio / colours.length, 0), c)))   
   
   def tToB(retObj: AnyRef,ratio: Double, colours: Colour*): PolySubj = Rect.h1(ratio).subjSeq(retObj,   
      colours.iMap((c, i) => FillPoly(Rect.tL( -ratio / 2, + 0.5, ratio, 1.0 / colours.length).slate(0, - i.toDouble / colours.length), c)))      
   
   import Colour._
   val armenia = lToR("Armenia flag", 2, Red, Blue, Gold)     
   val austria = tToB("Austria flag", 1.5, Black, Yellow)
   val belgium = lToR("Belgium flag", 15.0 / 13.0, Black, Yellow, Red)
   val chad = lToR("Chad flag", 1.5, Blue, Yellow, Red)
   val chinaRepublic =
   {
      val blueRect = Rect.tL(Vec2(- 0.75, 0.5), 0.75, 0.5).fill(DarkBlue)
      Rect(1.5).fillSubj("China", Red).addElem(blueRect)
   }
   val englishRed = Colour.fromInts(204, 0, 0)
   val england =
   {
      val redCross = Rect.cross(2, 1, 0.2).map(_.fill(englishRed))
      Rect(2, 1).fillSubj(" flag", White).addElems(redCross)
   }   
   val france = lToR("France flag", 1.5, Blue, White, Red)
   
   val germany = tToB("Germany flag", 5 / 3.0, Black, Red, Gold)
   val nazi: PolySubj =
   {
         val poly = Rect(5 / 3.0, 1)
         val bar = FillPoly(Rect.bCen(0.1, 0.2), Black)
         val arm = FillPoly(Rect.tL(-1.0 / 20, 0.25, 6.0 / 20, 0.1), Black)         
         val cross = Seq(bar, arm).p45.flatRCross
         val s1: Seq[CanvEl[_]] = Seq(
               FillPoly(poly, Red),
               Circle.segs(6.0 /8).fill(White)) ++
               cross      
         poly.subjSeq("Swastika", s1)
   }
   val germany1871 = tToB("Germany flag", 1.5, Black, White, Red)   
   val italy  = lToR("Italy flag", 1.5, Green, White, Red)   
   val ireland = lToR("Ireland flag", 2, Green, White, Orange)   
   val japan =
   {
      val poly = Rect(1.5, 1)
      val s1 = Seq(poly.fill(White), Circle.segs(0.6).fill(Colour.fromInts(188, 0 ,45)))
      poly.subjSeq("Japan Flag", s1)         
   }   
   val russia = tToB("Russia flag", 1.5, White, Blue, Red)   
   val soviet =
   {
      val star = Star5.ptUpYCentred.scale(0.4).fill(Gold)
      Rect(1.5, 1).fillSubj("Soviet flag simplified", Red).addElem(star)
   }  
   
   val uk =
   {
      val xd = math.sqrt(5) / 30.0//hypotenuse sqrt(2 * 2 + 1 * 1)
      val yd = math.sqrt(1.25) / 30.0//hypotenuse Sqrt(1 * 1 + 0.5 * 0.5)      
      val ywc = 5.0 /30 //top of White cross bar
      val xDiag = 10.0 /30.0//ywc * 2 where diag crosses ywc
      val b1 = Vec2s.xy(5.0/30, 0.5, 1 - xd * 3, 0.5, 1.0/6.0, ywc + yd)
      val b2 = Vec2s.xy(xDiag + 3 * xd, ywc, 1, 0.5 - yd * 3 , 1, ywc)
      val r1: Vec2s = Vec2s.xy(-1, 0.5, - xDiag, ywc, -(xDiag + xd * 2), ywc, -1, 0.5 - (yd * 2))
      val r2: Vec2s = Vec2s.xy(xDiag - xd * 2, ywc,  1 - xd * 2,  0.5, 1, 0.5, xDiag, ywc)
      val reds = Seq(r1, r2).map(_.fill(englishRed)).flatWithNegate
      val blues = Seq(b1, b2).map(_.fill(Colour.fromInts(0, 0, 102))).flatMirror4
      england.addElems(blues ++ reds).mutObj("United Kingdom flag")
   }
   val us =
   {
      val blueRect = Rect.tL(Vec2(-0.95, 0.5), 0.76, 7.0/ 13).fill(DarkBlue)
      Rect(1.9).fillSubj("United States Flag", Pink).addElem(blueRect)
   }
   def white = Rect(1.5).fillSubj("White flag", White)
}