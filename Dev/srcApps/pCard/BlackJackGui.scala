/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCard
import geom._, pgui._, Colour.Black

case class BlackJackGui(canv: CanvasPlatform) extends CanvasNoPanels("BlackJack")
{      
   val (hand, deck) = Card.newShuffled.takeCards(5)
   hand.iMap{ (i, c) => TextGraphic(c.unicode.mkString, 100, 50 + 100 * i pp 100, c.suitColour) }
   
   def clubFill(): GraphicElems =
   {
      val rad: Double = 0.55
      val circ3: PolygonGen = PolygonGen(0 pp rad, - rad * Sin60 pp - rad * Sin30, rad * Sin60 pp - rad * Sin30).scale(0.5)
      val c3 = circ3.slateY(0.06).vertsMap(cen => Circle(2 * rad * 0.46, cen).fill(Black))
      val rect: PolygonFill = TrapezoidIsosceles(0.35, 0.2, 0.5).slateY(- 0.28).fill(Black)
      c3 +% rect
   }      
   repaint(clubFill().scale(400) )//++ canv.gridLines2Colours(200, Colour.Red, Colour.Blue))
   //disp.repaint(Seq(Diamond().scale(400).fillDraw(Red, 4, Green)) ++ disp.gridLines2(200, Colour.Red, Colour.Blue))
}

trait BJack 
{
  val dollars: Int

  def checkScore(cards: Cards): Result =
  {
    val ap: (Int, Int) = cards.foldLeft((0, 0))((acc, n) =>  n.rank.value match
    { case 11 | 12 | 13 => (acc._1, acc._2 + 10)
      case 1 => (acc._1 + 1, acc._2 + 1)
      case c => (acc._1, acc._2 + c)
    })

    val seq = (0 to ap._1).map(_ * 10 + ap._2)
    
    seq match
    { case r if r.forall(_ > 21) => Bust(r.min)
      case r if (cards.length == 2) && r.max == 21 => BJResult
      case r => OtherResult(r.filter(_ <= 21).max)
    }
  }
}