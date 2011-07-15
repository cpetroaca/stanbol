/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
/* Generated By:JavaCC: Do not edit this line. RuleParserTokenManager.java */
package org.apache.stanbol.rules.manager.parse;

/** Token Manager. */
public class RuleParserTokenManager implements RuleParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x200000000000L) != 0L)
            return 8;
         if ((active0 & 0x100L) != 0L)
            return 1;
         if ((active0 & 0x3fbfffff820L) != 0L)
         {
            jjmatchedKind = 49;
            return 1;
         }
         return -1;
      case 1:
         if ((active0 & 0x2000000e000L) != 0L)
            return 1;
         if ((active0 & 0x1fbffff1800L) != 0L)
         {
            if (jjmatchedPos != 1)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 1;
            }
            return 1;
         }
         return -1;
      case 2:
         if ((active0 & 0x27a840000L) != 0L)
            return 1;
         if ((active0 & 0x3f9857b1800L) != 0L)
         {
            if (jjmatchedPos != 2)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 2;
            }
            return 1;
         }
         return -1;
      case 3:
         if ((active0 & 0x10000000800L) != 0L)
            return 1;
         if ((active0 & 0x2f98d7f1000L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 3;
            return 1;
         }
         return -1;
      case 4:
         if ((active0 & 0x808000000L) != 0L)
            return 1;
         if ((active0 & 0x2f1857f1000L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 4;
            return 1;
         }
         return -1;
      case 5:
         if ((active0 & 0x2f1807d1000L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 5;
            return 1;
         }
         if ((active0 & 0x5020000L) != 0L)
            return 1;
         return -1;
      case 6:
         if ((active0 & 0xf1807c1000L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 6;
            return 1;
         }
         if ((active0 & 0x20000010000L) != 0L)
            return 1;
         return -1;
      case 7:
         if ((active0 & 0x11803c1000L) != 0L)
         {
            if (jjmatchedPos != 7)
            {
               jjmatchedKind = 49;
               jjmatchedPos = 7;
            }
            return 1;
         }
         if ((active0 & 0xe000400000L) != 0L)
            return 1;
         return -1;
      case 8:
         if ((active0 & 0x1000200000L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 8;
            return 1;
         }
         if ((active0 & 0x81801c1000L) != 0L)
            return 1;
         return -1;
      case 9:
         if ((active0 & 0x1000000000L) != 0L)
         {
            jjmatchedKind = 49;
            jjmatchedPos = 9;
            return 1;
         }
         if ((active0 & 0x200000L) != 0L)
            return 1;
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         return jjStopAtPos(0, 42);
      case 34:
         return jjStartNfaWithStates_0(0, 45, 8);
      case 40:
         return jjStopAtPos(0, 43);
      case 41:
         return jjStopAtPos(0, 44);
      case 43:
         return jjStopAtPos(0, 10);
      case 44:
         return jjStopAtPos(0, 9);
      case 45:
         return jjMoveStringLiteralDfa1_0(0x20L);
      case 46:
         return jjStartNfaWithStates_0(0, 8, 1);
      case 58:
         return jjStopAtPos(0, 6);
      case 61:
         return jjStopAtPos(0, 7);
      case 91:
         return jjStopAtPos(0, 46);
      case 93:
         return jjStopAtPos(0, 47);
      case 94:
         return jjStopAtPos(0, 34);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x1001000000L);
      case 100:
         return jjMoveStringLiteralDfa1_0(0x1000L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x400000L);
      case 103:
         return jjMoveStringLiteralDfa1_0(0x4000L);
      case 104:
         return jjMoveStringLiteralDfa1_0(0x2000000L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x20000008000L);
      case 108:
         return jjMoveStringLiteralDfa1_0(0x100922000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0xc8010000L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x10000000000L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0xe230240800L);
      case 117:
         return jjMoveStringLiteralDfa1_0(0x800080000L);
      case 118:
         return jjMoveStringLiteralDfa1_0(0x4000000L);
      default :
         return jjMoveNfa_0(2, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 62:
         if ((active0 & 0x20L) != 0L)
            return jjStopAtPos(1, 5);
         break;
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x86000800L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x830000L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x800400000L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x149100000L);
      case 112:
         return jjMoveStringLiteralDfa2_0(active0, 0xe000080000L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x11000000000L);
      case 115:
         if ((active0 & 0x8000L) != 0L)
         {
            jjmatchedKind = 15;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x20000000000L);
      case 116:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(1, 13, 1);
         else if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(1, 14, 1);
         return jjMoveStringLiteralDfa2_0(active0, 0x200200000L);
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x30040000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 66:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000000000L);
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0xe000200000L);
      case 98:
         if ((active0 & 0x20000000L) != 0L)
         {
            jjmatchedKind = 29;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x40000L);
      case 99:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000000L);
      case 100:
         return jjMoveStringLiteralDfa3_0(active0, 0x400000L);
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000000000L);
      case 102:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000000L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000000L);
      case 109:
         if ((active0 & 0x10000000L) != 0L)
            return jjStartNfaWithStates_0(2, 28, 1);
         return jjMoveStringLiteralDfa3_0(active0, 0x80000800L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x1020000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000000000L);
      case 112:
         return jjMoveStringLiteralDfa3_0(active0, 0x80000L);
      case 114:
         if ((active0 & 0x200000000L) != 0L)
            return jjStartNfaWithStates_0(2, 33, 1);
         break;
      case 115:
         if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(2, 25, 1);
         break;
      case 116:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(2, 23, 1);
         else if ((active0 & 0x40000000L) != 0L)
         {
            jjmatchedKind = 30;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x8000000L);
      case 119:
         return jjMoveStringLiteralDfa3_0(active0, 0x110000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 78:
         return jjMoveStringLiteralDfa4_0(active0, 0x10000L);
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x1100000000L);
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x1000000L);
      case 101:
         if ((active0 & 0x800L) != 0L)
            return jjStartNfaWithStates_0(3, 11, 1);
         return jjMoveStringLiteralDfa4_0(active0, 0x88180000L);
      case 102:
         return jjMoveStringLiteralDfa4_0(active0, 0x1000L);
      case 103:
         return jjMoveStringLiteralDfa4_0(active0, 0x20000L);
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x20000000000L);
      case 111:
         return jjMoveStringLiteralDfa4_0(active0, 0x800000000L);
      case 112:
         if ((active0 & 0x10000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 40, 1);
         break;
      case 114:
         return jjMoveStringLiteralDfa4_0(active0, 0xe000200000L);
      case 115:
         return jjMoveStringLiteralDfa4_0(active0, 0x440000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x4000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 87:
         return jjMoveStringLiteralDfa5_0(active0, 0x400000L);
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x20001000000L);
      case 101:
         return jjMoveStringLiteralDfa5_0(active0, 0x4001000L);
      case 108:
         return jjMoveStringLiteralDfa5_0(active0, 0x100000000L);
      case 110:
         if ((active0 & 0x800000000L) != 0L)
            return jjStartNfaWithStates_0(4, 35, 1);
         break;
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0x10000L);
      case 113:
         return jjMoveStringLiteralDfa5_0(active0, 0xe000000000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x180000L);
      case 115:
         return jjMoveStringLiteralDfa5_0(active0, 0x80000000L);
      case 116:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000260000L);
      case 120:
         if ((active0 & 0x8000000L) != 0L)
            return jjStartNfaWithStates_0(4, 27, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 67:
         return jjMoveStringLiteralDfa6_0(active0, 0x180000L);
      case 100:
         return jjMoveStringLiteralDfa6_0(active0, 0x10000L);
      case 101:
         return jjMoveStringLiteralDfa6_0(active0, 0x1000000000L);
      case 104:
         if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(5, 17, 1);
         break;
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x400000L);
      case 108:
         return jjMoveStringLiteralDfa6_0(active0, 0xe000000000L);
      case 110:
         return jjMoveStringLiteralDfa6_0(active0, 0x20100000000L);
      case 112:
         return jjMoveStringLiteralDfa6_0(active0, 0x80000000L);
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x41000L);
      case 115:
         if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(5, 26, 1);
         return jjMoveStringLiteralDfa6_0(active0, 0x200000L);
      case 116:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(5, 24, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 45:
         return jjMoveStringLiteralDfa7_0(active0, 0xe000000000L);
      case 76:
         return jjMoveStringLiteralDfa7_0(active0, 0x1000000000L);
      case 87:
         return jjMoveStringLiteralDfa7_0(active0, 0x200000L);
      case 97:
         return jjMoveStringLiteralDfa7_0(active0, 0x180180000L);
      case 101:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(6, 16, 1);
         return jjMoveStringLiteralDfa7_0(active0, 0x1000L);
      case 105:
         return jjMoveStringLiteralDfa7_0(active0, 0x40000L);
      case 107:
         if ((active0 & 0x20000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 41, 1);
         break;
      case 116:
         return jjMoveStringLiteralDfa7_0(active0, 0x400000L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa8_0(active0, 0x1000000000L);
      case 99:
         if ((active0 & 0x2000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 37, 1);
         return jjMoveStringLiteralDfa8_0(active0, 0x80000000L);
      case 100:
         if ((active0 & 0x4000000000L) != 0L)
         {
            jjmatchedKind = 38;
            jjmatchedPos = 7;
         }
         return jjMoveStringLiteralDfa8_0(active0, 0x8000000000L);
      case 104:
         if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(7, 22, 1);
         break;
      case 105:
         return jjMoveStringLiteralDfa8_0(active0, 0x200000L);
      case 109:
         return jjMoveStringLiteralDfa8_0(active0, 0x100000000L);
      case 110:
         return jjMoveStringLiteralDfa8_0(active0, 0x41000L);
      case 115:
         return jjMoveStringLiteralDfa8_0(active0, 0x180000L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
private int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 98:
         return jjMoveStringLiteralDfa9_0(active0, 0x1000000000L);
      case 100:
         if ((active0 & 0x8000000000L) != 0L)
            return jjStartNfaWithStates_0(8, 39, 1);
         break;
      case 101:
         if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(8, 19, 1);
         else if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(8, 20, 1);
         else if ((active0 & 0x80000000L) != 0L)
            return jjStartNfaWithStates_0(8, 31, 1);
         else if ((active0 & 0x100000000L) != 0L)
            return jjStartNfaWithStates_0(8, 32, 1);
         break;
      case 103:
         if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(8, 18, 1);
         break;
      case 116:
         if ((active0 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(8, 12, 1);
         return jjMoveStringLiteralDfa9_0(active0, 0x200000L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
private int jjMoveStringLiteralDfa9_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa10_0(active0, 0x1000000000L);
      case 104:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(9, 21, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
private int jjMoveStringLiteralDfa10_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 108:
         if ((active0 & 0x1000000000L) != 0L)
            return jjStartNfaWithStates_0(10, 36, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 16;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((0x3ff600000000000L & l) != 0L)
                  {
                     if (kind > 49)
                        kind = 49;
                     jjCheckNAdd(1);
                  }
                  else if (curChar == 37)
                     jjCheckNAdd(11);
                  else if (curChar == 34)
                     jjCheckNAdd(8);
                  else if (curChar == 60)
                     jjCheckNAdd(5);
                  else if (curChar == 63)
                     jjCheckNAdd(3);
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 48)
                        kind = 48;
                     jjCheckNAdd(0);
                  }
                  break;
               case 0:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 48)
                     kind = 48;
                  jjCheckNAdd(0);
                  break;
               case 1:
                  if ((0x3ff600000000000L & l) == 0L)
                     break;
                  if (kind > 49)
                     kind = 49;
                  jjCheckNAdd(1);
                  break;
               case 3:
                  if ((0x3ff200000000000L & l) == 0L)
                     break;
                  if (kind > 50)
                     kind = 50;
                  jjCheckNAdd(3);
                  break;
               case 4:
                  if (curChar == 60)
                     jjCheckNAdd(5);
                  break;
               case 5:
                  if ((0x7ffe30800000000L & l) != 0L)
                     jjCheckNAddTwoStates(5, 6);
                  break;
               case 6:
                  if (curChar == 62 && kind > 51)
                     kind = 51;
                  break;
               case 7:
                  if (curChar == 34)
                     jjCheckNAdd(8);
                  break;
               case 8:
                  if ((0x87ffe03b00000000L & l) != 0L)
                     jjCheckNAddTwoStates(8, 9);
                  break;
               case 9:
                  if (curChar == 34 && kind > 52)
                     kind = 52;
                  break;
               case 10:
                  if (curChar == 37)
                     jjCheckNAdd(11);
                  break;
               case 11:
                  if ((0xf7fffb7f00000600L & l) != 0L)
                     jjCheckNAddTwoStates(11, 12);
                  break;
               case 12:
                  if (curChar == 37 && kind > 53)
                     kind = 53;
                  break;
               case 13:
                  if (curChar == 58)
                     jjCheckNAdd(14);
                  break;
               case 14:
                  if ((0x3ff600000000000L & l) == 0L)
                     break;
                  if (kind > 54)
                     kind = 54;
                  jjCheckNAdd(14);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 49)
                        kind = 49;
                     jjCheckNAdd(1);
                  }
                  if (curChar == 95)
                     jjstateSet[jjnewStateCnt++] = 13;
                  break;
               case 1:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 49)
                     kind = 49;
                  jjCheckNAdd(1);
                  break;
               case 3:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 50)
                     kind = 50;
                  jjstateSet[jjnewStateCnt++] = 3;
                  break;
               case 5:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                     jjAddStates(0, 1);
                  break;
               case 8:
                  if ((0x7fffffe97fffffeL & l) != 0L)
                     jjAddStates(2, 3);
                  break;
               case 11:
                  if ((0x3ffffffe97fffffeL & l) != 0L)
                     jjAddStates(4, 5);
                  break;
               case 14:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 54)
                     kind = 54;
                  jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 15:
                  if (curChar == 95)
                     jjstateSet[jjnewStateCnt++] = 13;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 16 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   5, 6, 8, 9, 11, 12, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, "\55\76", "\72", "\75", "\56", "\54", "\53", 
"\163\141\155\145", "\144\151\146\146\145\162\145\156\164", "\154\164", "\147\164", "\151\163", 
"\156\145\167\116\157\144\145", "\154\145\156\147\164\150", "\163\165\142\163\164\162\151\156\147", 
"\165\160\160\145\162\103\141\163\145", "\154\157\167\145\162\103\141\163\145", 
"\163\164\141\162\164\163\127\151\164\150", "\145\156\144\163\127\151\164\150", "\154\145\164", 
"\143\157\156\143\141\164", "\150\141\163", "\166\141\154\165\145\163", "\156\157\164\145\170", 
"\163\165\155", "\163\165\142", "\156\157\164", "\156\141\155\145\163\160\141\143\145", 
"\154\157\143\141\154\156\141\155\145", "\163\164\162", "\136", "\165\156\151\157\156", 
"\143\162\145\141\164\145\114\141\142\145\154", "\163\160\141\162\161\154\55\143", "\163\160\141\162\161\154\55\144", 
"\163\160\141\162\161\154\55\144\144", "\160\162\157\160", "\151\163\102\154\141\156\153", "\41", "\50", "\51", 
"\42", "\133", "\135", null, null, null, null, null, null, null, };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0x7fffffffffffe1L, 
};
static final long[] jjtoSkip = {
   0x1eL, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[16];
private final int[] jjstateSet = new int[32];
protected char curChar;
/** Constructor. */
public RuleParserTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public RuleParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 16; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

}
