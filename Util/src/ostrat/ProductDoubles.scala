/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ProductDoubles[A] extends Any with ProductValues[A]
{
  def arr: Array[Double]
  def arrLen = arr.length 
}