/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

class LongImplicit (val thisLong: Long) extends AnyVal
{
   def million: Long = thisLong * 1000000l
   def billion: Long = thisLong * 1000000000l
}