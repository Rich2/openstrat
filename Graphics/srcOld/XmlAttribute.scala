/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pWeb

/** Factory object for Xml Attribute. Case class not used to make it easy to inherit from */
object XAtt
{
   def apply (name: String, value: String): XAtt = new XAtt(name, value)   
}

/** Xml Attribute. Html and Svg elements do not have their own attribute traits and use Xml Attributes for the default case.   */
class XAtt (name: String, value: String) 
{
   def out(indent: Int): String = name + "=" + value.enquote1
   def multiLine: Boolean = false
}
