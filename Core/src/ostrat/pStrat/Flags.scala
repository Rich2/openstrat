/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._
import Colour._

object FlagsZ
{
   /** Equal width vertical bands. width ratio should normally be greater than 1.0 */
   def leftToRight(retObj: AnyRef, ratio: Double, colours: Colour*): PolySubj =  Rectangle(ratio, 1).subjSeq(evObj = retObj,
         colours.iMap((colour, i) => Rectangle.fromTL(ratio / colours.length, 1, -ratio / 2 vv + 0.5).slate(i * ratio / colours.length, 0).fill(colour)))   
         
    /** Equal height horrisontal bands. width ratio should normally be greater than 1.0 */     
   def topToBottom(retObj: AnyRef,ratio: Double, colours: Colour*): PolySubj = Rectangle(ratio, 1).subjSeq(retObj,   
      colours.iMap((colour, i) => Rectangle.fromTL(ratio, 1.0 / colours.length, -ratio / 2 vv + 0.5).slate(0, - i.toDouble / colours.length).fill(colour)))      
      
   val austria = topToBottom("Austria flag", 1.5, Black, Yellow)
   val belgium = leftToRight("Belgium flag", 15.0 / 13.0, Black, Yellow, Red)
   val chad = leftToRight("Chad flag", 1.5, Blue, Yellow, Red)
   val chinaRepublic =
   {
      val blueRect = Rectangle.fromTL(0.75, 0.5, - 0.75 vv 0.5).fill(DarkBlue)
      Rectangle(1.5, 1).fillSubj("China", Red).addElem(blueRect)
   }
   val englishRed = Colour.fromInts(204, 0, 0)
   val england =
   {
      val redCross = Rectangle.cross(2, 1, 0.2).map(_.fill(englishRed))
      Rectangle(2, 1).fillSubj(" flag", White).addElems(redCross)
   }   
   val france = leftToRight("France flag", 1.5, Blue, White, Red)
   
   val germany = topToBottom("Germany flag", 5 / 3.0, Black, Red, Gold)
   val nazi: PolySubj =
   {
         val poly = Rectangle(5 / 3.0, 1)
         val bar = Rectangle.fromBC(0.1, 0.2).fill(Black)
         val arm = Rectangle.fromTL(6.0 / 20, 0.1, -1.0 / 20 vv 0.25).fill(Black)         
         val cross = List(bar, arm).anti45.flatMap(_.rCross)//  flatRCross
         val s1: List[PaintElem[_]] = List(
               poly.fill(Red),
               Circle.segs(6.0 /8).fill(White)) ++
               cross      
         poly.subjSeq("Swastika", s1)
   }
   val germany1871 = topToBottom("Germany flag", 1.5, Black, White, Red)   
   val italy  = leftToRight("Italy flag", 1.5, Green, White, Red)   
   val ireland = leftToRight("Ireland flag", 2, Green, White, Orange)   
   val japan =
   {
      val poly = Rectangle(1.5, 1)
      val s1 = List(poly.fill(White), Circle.segs(0.6).fill(Colour.fromInts(188, 0 ,45)))
      poly.subjSeq("Japan Flag", s1)         
   }   
   val russia = topToBottom("Russia flag", 1.5, White, Blue, Red)   
   val soviet =
   {
      val star = Star5.ptUpYCentred.scale(0.4).fill(Gold)
      Rectangle(1.5, 1).fillSubj("Soviet flag simplified", Red).addElem(star)
   }  
   
   val uk =
   {
      val xd = math.sqrt(5) / 30.0//hypotenuse sqrt(2 * 2 + 1 * 1)
      val yd = math.sqrt(1.25) / 30.0//hypotenuse Sqrt(1 * 1 + 0.5 * 0.5)      
      val ywc = 5.0 /30 //top of White cross bar
      val xDiag = 10.0 /30.0//ywc * 2 where diag crosses ywc
      val b1 = Polygon(5.0/30 vv 0.5,
            1 - xd * 3 vv 0.5,
            1.0/ 6.0 vv ywc + yd)            
      val b2 = Polygon(xDiag + 3 * xd vv ywc,
            1 vv 0.5 - yd * 3,
            1 vv ywc)
      val r1: Polygon = Polygon(-1 vv 0.5,
            - xDiag vv ywc,
            -(xDiag + xd * 2) vv ywc,
            -1 vv 0.5 - (yd * 2))
      val r2: Polygon = Polygon(xDiag - xd * 2 vv ywc,
            1 - xd * 2 vv  0.5,
            1 vv 0.5,
            xDiag vv ywc)
      val reds1 = List(r1, r2).map(_.fill(englishRed))      
      val reds = reds1.flatMap(e => List(e, e.fTrans(- _)))//.flatWithNegate
      val blues = List(b1, b2).map(_.fill(Colour.fromInts(0, 0, 102))).flatMirror4
      england.addElems(blues ++ reds).mutObj("United Kingdom flag")
   }
   val us =
   {
      val blueRect = Rectangle.fromTL(0.76, 7.0/ 13, -0.95 vv 0.5).fill(DarkBlue)
      Rectangle(1.9, 1).fillSubj("United States Flag", Pink).addElem(blueRect)
   }
   def white = Rectangle(1.5, 1).fillSubj("White flag", White)
}