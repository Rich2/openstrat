/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait Show extends Any
{ def typeStr: String
  def str: String
  def strr(decimalPlaces: Int): String = str
}