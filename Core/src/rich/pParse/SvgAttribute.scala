/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pParse

object SvgAtt 
{
   def apply(name: String, value: String): SvgAtt = new SvgAtt(name, value)
}

class SvgAtt(name: String, value: String) extends XAtt(name, value)