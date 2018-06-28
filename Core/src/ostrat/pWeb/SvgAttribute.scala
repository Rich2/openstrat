/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pWeb

object SvgAtt 
{
   def apply(name: String, value: String): SvgAtt = new SvgAtt(name, value)
}

class SvgAtt(name: String, value: String) extends XAtt(name, value)