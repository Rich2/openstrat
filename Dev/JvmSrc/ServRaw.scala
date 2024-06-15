/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb.*, java.net.*, java.io.{ BufferedReader, InputStreamReader }

object ServRaw extends App
{ deb("Starting")
  val servSock: ServerSocket = new ServerSocket(8080)
  var numConns: Int = 0
  while(true)
  { val sock: Socket = servSock.accept()
    val conn = new ConnSesh(numConns, sock)
    conn.run()
    numConns += 1
  }
  servSock.close()
  deb("Finishing")
}

class ConnSesh(val cNum: Int, val sock: Socket) extends Runnable
{
  override def run(): Unit =
  { val readbuf: BufferedReader = new BufferedReader(new java.io.InputStreamReader(sock.getInputStream()))
    val outSt = sock.getOutputStream
    var reqNum = 0
    while(true)
    { println(s"Req $cNum, $reqNum start")
      var line: String = null
      val acc = StringBuff()
      var continue = true
      while (continue)
      { line = readbuf.readLine
        if(line == null)
          if (acc.nonEmpty) continue = false
          else Thread.sleep(10)
        else
          if(line == "")
          { continue = false
            println("Empty line")
          }
          else
          { acc.grow(line)
            println(line)
          }
      }
      val req = HttpReq(acc)
      req match
      { case Good(hrg: HttpReqGet) => hrg.uri match
        { case "/" =>
          { val repStr: String = IndexPage.httpResp("localhost").out
            deb("About to send Response Index page")
            outSt.write(repStr.getBytes)
            outSt.write("\n".getBytes())
            outSt.flush()
            deb("Sent Response Index page")
          }

          case "/Documentation/documentation.css" =>
          { val repStr: String = CssDocmentation.httpResp("localhost").out
            deb("About to send Response doc css")
            outSt.write(repStr.getBytes)
            outSt.write("\n".getBytes())
            outSt.flush()
            deb("Response doc css")
          }
          case id => deb(s"Other URI |$id|")
        }
        case _ => deb("Other match")
      }
      deb("Response sent.")
      reqNum += 1
    }
    readbuf.close
  }
}