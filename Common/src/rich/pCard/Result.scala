/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pCard

sealed trait Result
{
   def out: String
}
case class Bust(res: Int) extends Result
{
   def out = "Bust" -- res.toString
}

trait NotBust  extends Result { def res: Int }
case object BJResult extends Result { def out = "Blackjack" }

case class OtherResult(res: Int) extends NotBust
{
   def out = res.toString
}
