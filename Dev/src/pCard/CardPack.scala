/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pCard
import geom._, Colour._

object Card
{ type Cards = Seq[Card]
  def apply(rankNum: Int, suit: Suit): Option[Card] = rankNum match
  { case r if r < 1 => None
    case r if r > 13 => None
    case r => Some(new Card(Rank(rankNum), suit))
  }
   def noCards: Cards = Seq[Card]()
   val suits: Seq[Suit] = Seq(Spade, Heart, Diamond, Club)
   def newSeq(pairs: (Int, Suit) *): Seq[Card] = pairs.map(e => Card(e._1, e._2)).flatten
   def newPack: Seq[Card] = (for (s <- suits; n <- 1 to 13) yield Card(n, s)).toSeq.flatten
   def newShuffled = newPack.shuffle
   def multiShuffled(numberOfPacks: Int): Cards = (0 until numberOfPacks).flatMap(_ => newPack).shuffle
   implicit class ImplicitPackOfCards(theseCards: Cards)
   {
      def removeRandomCard: (Card, Seq[Card]) =
      {        
        val ind = scala.util.Random.nextInt(theseCards.length)        
        (theseCards(ind), theseCards.dropRight(theseCards.length - ind) ++ theseCards.drop(ind + 1))
      }
      def shuffle: Seq[Card] =
      {
         def loop(rem: Seq[Card], acc: Seq[Card]): Seq[Card] = rem match
         {
            case Seq() => acc
            case s =>
            {
               val (card, rest) = s.removeRandomCard
               loop(rest, acc :+ card)                              
            }
         }
         loop(theseCards, Nil)
      }
      //def takeCard(hand: Card): Cards) = theseCards.head, theseCards.tail)
      def takeCards(num: Int, hand: Cards = Seq()): (Cards, Cards) = (hand ++ theseCards.take(num), theseCards.drop(num))
      //def cardsTo(numTransfered: Int, toHand: Cards): (Cards, Cards) = 
   }   
}

case class DispCard(unicode: Seq[Char], colour: Colour)// extends CanvObj
{ def cardScale: Int = 100
  def objWidth: Double = cardScale
  def objHeight: Double = cardScale * 1.15
  //def acts = Seq(TextFill(unicode.mkString, cardScale, colour, Vec2(objWidth * 0.6, objHeight * 0.8)))
  //def elems: Seq[CanvEl] = Seq()
}

case class Card(rank: Rank, suit: Suit)
{ override def toString: String = rank.toString + "Of" + suit.toString + "s"

  def suitColour: Colour = suit match
  { case Spade | Club => Black
    case Heart | Diamond => Red
  }
   
  def unicode: Array[Char] =
  { //The Unicode sets include a Knight rank between Jack and Queen
    val offset = if (rank.value < 12) rank.value else rank.value + 1
    suit match
    {//This uses unicode >= 0x10000 which require 2 java /javascript chars to encode one char
      case Spade => java.lang.Character.toChars(0x1F0A0 + offset)
      case Heart => java.lang.Character.toChars(0x1F0B0 + offset)
      case Diamond => java.lang.Character.toChars(0x1F0C0 + offset)
      case Club => java.lang.Character.toChars(0x1F0D0 + offset)
    }
  }
  
//   def unicodeObj: CanvObj = DispCard(unicode, suitColour)
}