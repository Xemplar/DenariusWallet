package com.xemplarsoft.libs.tribus;


import java.util.Base64;

public class Tribus {
    public static int[] AES0 = bytes2Int32Buffer(b64Decode("pWNjxoR8fPiZd3fujXt79g3y8v+9a2vWsW9v3lTFxZFQMDBgAwEBAqlnZ859KytWGf7+52LX17Xmq6tNmnZ27EXKyo+dgoIfQMnJiYd9ffoV+vrv61lZsslHR44L8PD77K2tQWfU1LP9oqJf6q+vRb+cnCP3pKRTlnJy5FvAwJvCt7d1HP394a6Tkz1qJiZMWjY2bEE/P34C9/f1T8zMg1w0NGj0paVRNOXl0Qjx8fmTcXHic9jYq1MxMWI/FRUqDAQECFLHx5VlIyNGXsPDnSgYGDChlpY3DwUFCrWami8JBwcONhISJJuAgBs94uLfJuvrzWknJ07NsrJ/n3V16hsJCRKeg4MddCwsWC4aGjQtGxs2sm5u3O5aWrT7oKBb9lJSpE07O3Zh1ta3zrOzfXspKVI+4+PdcS8vXpeEhBP1U1OmaNHRuQAAAAAs7e3BYCAgQB/8/OPIsbF57Vtbtr5qatRGy8uN2b6+Z0s5OXLeSkqU1ExMmOhYWLBKz8+Fa9DQuyrv78XlqqpPFvv77cVDQ4bXTU2aVTMzZpSFhRHPRUWKEPn56QYCAgSBf3/+8FBQoEQ8PHi6n58l46ioS/NRUaL+o6NdwEBAgIqPjwWtkpI/vJ2dIUg4OHAE9fXx37y8Y8G2tnd12tqvYyEhQjAQECAa///lDvPz/W3S0r9Mzc2BFAwMGDUTEyYv7OzD4V9fvqKXlzXMRESIORcXLlfExJPyp6dVgn5+/Ec9PXqsZGTI511duisZGTKVc3PmoGBgwJiBgRnRT0+ef9zco2YiIkR+KipUq5CQO4OIiAvKRkaMKe7ux9O4uGs8FBQoed7ep+JeXrwdCwsWdtvbrTvg4NtWMjJkTjo6dB4KChTbSUmSCgYGDGwkJEjkXFy4XcLCn27T073vrKxDpmJixKiRkTmklZUxN+Tk04t5efIy5+fVQ8jIi1k3N263bW3ajI2NAWTV1bHSTk6c4KmpSbRsbNj6VlasB/T08yXq6s+vZWXKjnp69OmurkcYCAgQ1bq6b4h4ePBvJSVKci4uXCQcHDjxpqZXx7S0c1HGxpcj6OjLfN3doZx0dOghHx8+3UtLlty9vWGGi4sNhYqKD5BwcOBCPj58xLW1capmZszYSEiQBQMDBgH29vcSDg4co2Fhwl81NWr5V1eu0Lm5aZGGhhdYwcGZJx0dOrmenic44eHZE/j467OYmCszEREiu2lp0nDZ2amJjo4Hp5SUM7abmy0iHh48koeHFSDp6clJzs6H/1VVqngoKFB639+lj4yMA/ihoVmAiYkJFw0NGtq/v2Ux5ubXxkJChLhoaNDDQUGCsJmZKXctLVoRDw8ey7Cwe/xUVKjWu7ttOhYWLA=="));
    public static int[] AES1 = bytes2Int32Buffer(b64Decode("Y2PGpXx8+IR3d+6Ze3v2jfLy/w1ra9a9b2/escXFkVQwMGBQAQECA2dnzqkrK1Z9/v7nGdfXtWKrq03mdnbsmsrKj0WCgh+dycmJQH19+of6+u8VWVmy60dHjsnw8PsLra1B7NTUs2eiol/9r69F6pycI7+kpFP3cnLklsDAm1u3t3XC/f3hHJOTPa4mJkxqNjZsWj8/fkH39/UCzMyDTzQ0aFylpVH05eXRNPHx+QhxceKT2NirczExYlMVFSo/BAQIDMfHlVIjI0Zlw8OdXhgYMCiWljehBQUKD5qaL7UHBw4JEhIkNoCAG5vi4t896+vNJicnTmmysn/NdXXqnwkJEhuDgx2eLCxYdBoaNC4bGzYtbm7cslpatO6goFv7UlKk9js7dk3W1rdhs7N9zikpUnvj490+Ly9ecYSEE5dTU6b10dG5aAAAAADt7cEsICBAYPz84x+xsXnIW1u27Wpq1L7Ly41Gvr5n2Tk5cktKSpTeTEyY1FhYsOjPz4VK0NC7a+/vxSqqqk/l+/vtFkNDhsVNTZrXMzNmVYWFEZRFRYrP+fnpEAICBAZ/f/6BUFCg8Dw8eESfnyW6qKhL41FRovOjo13+QECAwI+PBYqSkj+tnZ0hvDg4cEj19fEEvLxj37a2d8Ha2q91ISFCYxAQIDD//+Ua8/P9DtLSv23NzYFMDAwYFBMTJjXs7MMvX1++4ZeXNaJERIjMFxcuOcTEk1enp1Xyfn78gj09ekdkZMisXV265xkZMitzc+aVYGDAoIGBGZhPT57R3NyjfyIiRGYqKlR+kJA7q4iIC4NGRozK7u7HKbi4a9MUFCg83t6neV5evOILCxYd29utduDg2zsyMmRWOjp0TgoKFB5JSZLbBgYMCiQkSGxcXLjkwsKfXdPTvW6srEPvYmLEppGROaiVlTGk5OTTN3l58ovn59UyyMiLQzc3blltbdq3jY0BjNXVsWROTpzSqalJ4Gxs2LRWVqz69PTzB+rqzyVlZcqvenr0jq6uR+kICBAYurpv1Xh48IglJUpvLi5cchwcOCSmplfxtLRzx8bGl1Ho6Msj3d2hfHR06JwfHz4hS0uW3b29YdyLiw2GiooPhXBw4JA+PnxCtbVxxGZmzKpISJDYAwMGBfb29wEODhwSYWHCozU1al9XV675ublp0IaGF5HBwZlYHR06J56eJ7nh4dk4+PjrE5iYK7MRESIzaWnSu9nZqXCOjgeJlJQzp5ubLbYeHjwih4cVkunpySDOzodJVVWq/ygoUHjf36V6jIwDj6GhWfiJiQmADQ0aF7+/Zdrm5tcxQkKExmho0LhBQYLDmZkpsC0tWncPDx4RsLB7y1RUqPy7u23WFhYsOg=="));
    public static int[] AES2 = bytes2Int32Buffer(b64Decode("Y8alY3z4hHx37pl3e/aNe/L/DfJr1r1rb96xb8WRVMUwYFAwAQIDAWfOqWcrVn0r/ucZ/te1YterTearduyadsqPRcqCH52CyYlAyX36h3367xX6WbLrWUeOyUfw+wvwrUHsrdSzZ9SiX/2ir0Xqr5wjv5ykU/ekcuSWcsCbW8C3dcK3/eEc/ZM9rpMmTGomNmxaNj9+QT/39QL3zINPzDRoXDSlUfSl5dE05fH5CPFx4pNx2Ktz2DFiUzEVKj8VBAgMBMeVUscjRmUjw51ewxgwKBiWN6GWBQoPBZovtZoHDgkHEiQ2EoAbm4Di3z3i680m6ydOaSeyf82ydeqfdQkSGwmDHZ6DLFh0LBo0LhobNi0bbtyyblq07lqgW/ugUqT2Ujt2TTvWt2HWs33OsylSeynj3T7jL15xL4QTl4RTpvVT0blo0QAAAADtwSztIEBgIPzjH/yxecixW7btW2rUvmrLjUbLvmfZvjlySzlKlN5KTJjUTFiw6FjPhUrP0Ltr0O/FKu+qT+Wq++0W+0OGxUNNmtdNM2ZVM4URlIVFis9F+ekQ+QIEBgJ//oF/UKDwUDx4RDyfJbqfqEvjqFGi81GjXf6jQIDAQI8Fio+SP62SnSG8nThwSDj18QT1vGPfvLZ3wbbar3XaIUJjIRAgMBD/5Rr/8/0O89K/bdLNgUzNDBgUDBMmNRPswy/sX77hX5c1opdEiMxEFy45F8STV8SnVfKnfvyCfj16Rz1kyKxkXbrnXRkyKxlz5pVzYMCgYIEZmIFPntFP3KN/3CJEZiIqVH4qkDurkIgLg4hGjMpG7scp7rhr07gUKDwU3qd53l684l4LFh0L26122+DbO+AyZFYyOnROOgoUHgpJkttJBgwKBiRIbCRcuORcwp9dwtO9btOsQ++sYsSmYpE5qJGVMaSV5NM35Hnyi3nn1TLnyItDyDduWTdt2rdtjQGMjdWxZNVOnNJOqUngqWzYtGxWrPpW9PMH9OrPJeplyq9levSOeq5H6a4IEBgIum/VunjwiHglSm8lLlxyLhw4JBymV/GmtHPHtMaXUcboyyPo3aF83XTonHQfPiEfS5bdS71h3L2LDYaLig+FinDgkHA+fEI+tXHEtWbMqmZIkNhIAwYFA/b3AfYOHBIOYcKjYTVqXzVXrvlXuWnQuYYXkYbBmVjBHTonHZ4nuZ7h2Tjh+OsT+Jgrs5gRIjMRadK7admpcNmOB4mOlDOnlJsttpsePCIehxWSh+nJIOnOh0nOVar/VShQeCjfpXrfjAOPjKFZ+KGJCYCJDRoXDb9l2r/m1zHmQoTGQmjQuGhBgsNBmSmwmS1ady0PHhEPsHvLsFSo/FS7bda7Fiw6Fg=="));
    public static int[] AES3 = bytes2Int32Buffer(b64Decode("xqVjY/iEfHzumXd39o17e/8N8vLWvWtr3rFvb5FUxcVgUDAwAgMBAc6pZ2dWfSsr5xn+/rVi19dN5qur7Jp2do9FysofnYKCiUDJyfqHfX3vFfr6sutZWY7JR0f7C/DwQeytrbNn1NRf/aKiReqvryO/nJxT96Sk5JZycptbwMB1wre34Rz9/T2uk5NMaiYmbFo2Nn5BPz/1Avf3g0/MzGhcNDRR9KWl0TTl5fkI8fHik3Fxq3PY2GJTMTEqPxUVCAwEBJVSx8dGZSMjnV7DwzAoGBg3oZaWCg8FBS+1mpoOCQcHJDYSEhubgIDfPeLizSbr605pJyd/zbKy6p91dRIbCQkdnoODWHQsLDQuGho2LRsb3LJubrTuWlpb+6CgpPZSUnZNOzu3YdbWfc6zs1J7KSndPuPjXnEvLxOXhISm9VNTuWjR0QAAAADBLO3tQGAgIOMf/Px5yLGxtu1bW9S+amqNRsvLZ9m+vnJLOTmU3kpKmNRMTLDoWFiFSs/Pu2vQ0MUq7+9P5aqq7Rb7+4bFQ0Oa101NZlUzMxGUhYWKz0VF6RD5+QQGAgL+gX9/oPBQUHhEPDwlup+fS+OoqKLzUVFd/qOjgMBAQAWKj48/rZKSIbydnXBIODjxBPX1Y9+8vHfBtravddraQmMhISAwEBDlGv///Q7z879t0tKBTM3NGBQMDCY1ExPDL+zsvuFfXzWil5eIzERELjkXF5NXxMRV8qen/IJ+fnpHPT3IrGRkuuddXTIrGRnmlXNzwKBgYBmYgYGe0U9Po3/c3ERmIiJUfioqO6uQkAuDiIiMykZGxynu7mvTuLgoPBQUp3ne3rziXl4WHQsLrXbb29s74OBkVjIydE46OhQeCgqS20lJDAoGBkhsJCS45Fxcn13Cwr1u09ND76ysxKZiYjmokZExpJWV0zfk5PKLeXnVMufni0PIyG5ZNzfat21tAYyNjbFk1dWc0k5OSeCpqdi0bGys+lZW8wf09M8l6urKr2Vl9I56ekfprq4QGAgIb9W6uvCIeHhKbyUlXHIuLjgkHBxX8aamc8e0tJdRxsbLI+jooXzd3eicdHQ+IR8flt1LS2Hcvb0NhouLD4WKiuCQcHB8Qj4+ccS1tcyqZmaQ2EhIBgUDA/cB9vYcEg4OwqNhYWpfNTWu+VdXadC5uReRhoaZWMHBOicdHSe5np7ZOOHh6xP4+CuzmJgiMxER0rtpaalw2dkHiY6OM6eUlC22m5s8Ih4eFZKHh8kg6emHSc7Oqv9VVVB4KCilet/fA4+MjFn4oaEJgImJGhcNDWXav7/XMebmhMZCQtC4aGiCw0FBKbCZmVp3LS0eEQ8Pe8uwsKj8VFRt1ru7LDoWFg=="));

    public Tribus(){ }

    public Object[] digest(String str, int format, int output){
        Integer[] jh = (Integer[]) Jh.jhExports(new String[]{str}, 3, 2);
        Integer[] keccak = (Integer[]) Keccak.keccakExports(jh, 2, 1);
        Integer[] echo = (Integer[]) Echo.echoExports(keccak, 2, 2);
        int[] a = new int[echo.length];
        int i = 0;
        for(Integer x : echo) a[i++] = x;

        a = slice(a, 8);
        if (output == 2) {
            Integer[] ret = new Integer[a.length];
            i = 0;
            for(int x : a) ret[i++] = x;
            return ret;
        } else if (output == 1) {
            byte[] dat = int32Buffer2Bytes(a);
            Byte[] ret = new Byte[dat.length];
            i = 0;
            for(byte b : dat) ret[i++] = b;
            return ret;
        } else {
            return new String[]{int32ArrayToHexString(a)};
        }
    }

    public static final class Echo {
        public static int ECHO_BlockSize = 128;

        public static void subWords(int[][] W, int[] pK) {
            for (int n = 0; n < 16; n++) {
                int[] X = W[n];
                int[] Y = new int[4];
                AES_ROUND_LE(X, pK, Y);
                AES_ROUND_NOKEY_LE(Y, X);
                if ((pK[0] = t32(pK[0] + 1)) == 0) {
                    if ((pK[1] = t32(pK[1] + 1)) == 0)
                        if ((pK[2] = t32(pK[2] + 1)) == 0)
                            pK[3] = t32(pK[3] + 1);
                }
            }
        }

        public static int[][] shiftRow1(int[][] W, int a, int b, int c, int d) {
            int tmp;
            tmp = W[a][0];
            W[a][0] = W[b][0];
            W[b][0] = W[c][0];
            W[c][0] = W[d][0];
            W[d][0] = tmp;
            tmp = W[a][1];
            W[a][1] = W[b][1];
            W[b][1] = W[c][1];
            W[c][1] = W[d][1];
            W[d][1] = tmp;
            tmp = W[a][2];
            W[a][2] = W[b][2];
            W[b][2] = W[c][2];
            W[c][2] = W[d][2];
            W[d][2] = tmp;
            tmp = W[a][3];
            W[a][3] = W[b][3];
            W[b][3] = W[c][3];
            W[c][3] = W[d][3];
            W[d][3] = tmp;

            return W;
        }

        public static int[][] shiftRow2(int[][] W, int a, int b, int c, int d) {
            int tmp;
            tmp = W[a][0];
            W[a][0] = W[c][0];
            W[c][0] = tmp;
            tmp = W[b][0];
            W[b][0] = W[d][0];
            W[d][0] = tmp;
            tmp = W[a][1];
            W[a][1] = W[c][1];
            W[c][1] = tmp;
            tmp = W[b][1];
            W[b][1] = W[d][1];
            W[d][1] = tmp;
            tmp = W[a][2];
            W[a][2] = W[c][2];
            W[c][2] = tmp;
            tmp = W[b][2];
            W[b][2] = W[d][2];
            W[d][2] = tmp;
            tmp = W[a][3];
            W[a][3] = W[c][3];
            W[c][3] = tmp;
            tmp = W[b][3];
            W[b][3] = W[d][3];
            W[d][3] = tmp;

            return W;
        }

        public static int[][] shiftRow3(int[][] W, int a, int b, int c, int d) {
            return shiftRow1(W, d, c, b, a);
        }

        public static int[][] shiftRows(int[][] W) {
            shiftRow1(W, 1, 5, 9, 13);
            shiftRow2(W, 2, 6, 10, 14);
            shiftRow3(W, 3, 7, 11, 15);

            return W;
        }

        public static int[][] mixColumn(int[][] W, int ia, int ib, int ic, int id) {
            for (int n = 0; n < 4; n++) {
                int a = W[ia][n];
                int b = W[ib][n];
                int c = W[ic][n];
                int d = W[id][n];
                int ab = a ^ b;
                int bc = b ^ c;
                int cd = c ^ d;
                int abx = ((ab & (0x80808080)) >>> 7) * 27 ^
                        ((ab & (0x7F7F7F7F)) << 1);
                int bcx = ((bc & (0x80808080)) >>> 7) * 27 ^
                        ((bc & (0x7F7F7F7F)) << 1);
                int cdx = ((cd & (0x80808080)) >>> 7) * 27 ^
                        ((cd & (0x7F7F7F7F)) << 1);
                W[ia][n] = abx ^ bc ^ d;
                W[ib][n] = bcx ^ a ^ cd;
                W[ic][n] = cdx ^ ab ^ d;
                W[id][n] = abx ^ bcx ^ cdx ^ ab ^ c;
            }

            return W;
        }

        public static void finalize(CTX_ECHO ctx, int[][] W) {
            int[] int32Buf = swap32Array(bytes2Int32Buffer(ctx.buffer));
            for (int u = 0; u < 8; u++) {
                for (int v = 0; v < 4; v++) {
                    ctx.state[u][v] ^= int32Buf[u * 4 + v] ^ W[u][v] ^ W[u + 8][v];
                }
            }
        }

        public static void inputBlock(CTX_ECHO ctx, int[][] W) {
            buffer2Insert(W, 0, 0, ctx.state, 8, 4);
            int[] int32Buf = swap32Array(bytes2Int32Buffer(ctx.buffer));
            for (int u = 0; u < 8; u++) {
                W[u + 8][0] = (int32Buf[4 * u]);
                W[u + 8][1] = (int32Buf[4 * u + 1]);
                W[u + 8][2] = (int32Buf[4 * u + 2]);
                W[u + 8][3] = (int32Buf[4 * u + 3]);
            }
        }

        public static int[][] mixColumns(int[][] W) {
            mixColumn(W, 0, 1, 2, 3);
            mixColumn(W, 4, 5, 6, 7);
            mixColumn(W, 8, 9, 10, 11);
            mixColumn(W, 12, 13, 14, 15);

            return W;
        }

        public static void ROUND(int[][] W, int[] K) {
            subWords(W, K);
            shiftRows(W);
            mixColumns(W);
        }

        public static CTX_ECHO compress(CTX_ECHO ctx) {
            int[][] W = new int[16][];
            for (int i = 0; i < 16; i++) {
                W[i] = new int[4];
            }
            int[] K = new int[4];
            bufferInsert(K, 0, ctx.C, 4, 0);
            inputBlock(ctx, W);
            for (int u = 0; u < 10; u++) {
                ROUND(W, K);
            }
            finalize(ctx, W);
            return ctx;
        }

        public static void incrCounter(CTX_ECHO ctx, int val) {
            ctx.C[0] = t32(ctx.C[0] + t32(val));
            if (ctx.C[0] < t32(val)) {
                if ((ctx.C[1] = t32(ctx.C[1] + 1)) == 0) {
                    if ((ctx.C[2] = t32(ctx.C[2] + 1)) == 0) {
                        ctx.C[3] = t32(ctx.C[3] + 1);
                    }
                }
            }
        }

        public static void echoInit(CTX_ECHO ctx) {
            ctx.state = new int[8][];
            for (int i = 0; i < 8; i++) {
                ctx.state[i] = new int[4];
            }
            ctx.state[0][0] = 512;
            ctx.state[0][1] = ctx.state[0][2] = ctx.state[0][3] = 0;
            ctx.state[1][0] = 512;
            ctx.state[1][1] = ctx.state[1][2] = ctx.state[1][3] = 0;
            ctx.state[2][0] = 512;
            ctx.state[2][1] = ctx.state[2][2] = ctx.state[2][3] = 0;
            ctx.state[3][0] = 512;
            ctx.state[3][1] = ctx.state[3][2] = ctx.state[3][3] = 0;
            ctx.state[4][0] = 512;
            ctx.state[4][1] = ctx.state[4][2] = ctx.state[4][3] = 0;
            ctx.state[5][0] = 512;
            ctx.state[5][1] = ctx.state[5][2] = ctx.state[5][3] = 0;
            ctx.state[6][0] = 512;
            ctx.state[6][1] = ctx.state[6][2] = ctx.state[6][3] = 0;
            ctx.state[7][0] = 512;
            ctx.state[7][1] = ctx.state[7][2] = ctx.state[7][3] = 0;
            ctx.ptr = 0;
            ctx.C = new int[4];
            bufferSet(ctx.C, 0, 0, 4);
            ctx.buffer = new byte[ECHO_BlockSize];
        }

        public static void echo(CTX_ECHO ctx, byte[] data) {
            byte[] buf;
            int ptr;
            buf = ctx.buffer;
            ptr = ctx.ptr;
            int len = data.length;
            if (len < ctx.buffer.length - ptr) {
                bufferInsert(buf, ptr, data, data.length, 0);
                ptr += data.length;
                ctx.ptr = ptr;
                return;
            }
            while (len > 0) {
                int clen = ctx.buffer.length - ptr;
                if (clen > len) clen = len;
                bufferInsert(buf, ptr, data, clen, 0);
                ptr += clen;
                data = slice(data, clen);
                len -= clen;
                if (ptr == ctx.buffer.length) {
                    int[] int32Buf = bytes2Int32Buffer(buf);
                    incrCounter(ctx, 1024);
                    compress(ctx);
                    ptr = 0;
                }
            }
            ctx.ptr = ptr;
        }

        public static int[] echoClose(CTX_ECHO ctx) {
            int[] out = new int[16];
            byte[] buf = ctx.buffer;
            int len = ctx.buffer.length;
            int ptr = ctx.ptr;
            int elen = (ptr << 3);
            incrCounter(ctx, elen);
            byte[] cBytes = int32Buffer2Bytes(swap32Array(ctx.C));
            /*
             * If elen is zero, then this block actually contains no message
             * bit, only the first padding bit.
             */
            if (elen == 0) {
                ctx.C[0] = ctx.C[1] = ctx.C[2] = ctx.C[3] = 0;
            }
            buf[ptr++] = (byte) 0x80;

            bufferSet(buf, ptr, (byte) 0, len - ptr);
            if (ptr > (len - 18)) {
                compress(ctx);
                bufferSet(ctx.C, 0, 0, 4);
                bufferSet(buf, 0, (byte) 0, len);
            }
            buf[len - 17] = 2;
            bufferInsert(buf, len - 16, cBytes, 16, 0);
            compress(ctx);
            for (int u = 0; u < 4; u++) {
                for (int v = 0; v < 4; v++) {
                    out[u * 4 + v] = swap32(ctx.state[u][v]);
                }
            }


            return out;
        }

        public static Object[] echoExports(Object[] input, int format, int output) {
            byte[] msg;
            if (format == 1) {
                msg = new byte[input.length];
                int i = 0;
                for (Byte b : (Byte[]) input) msg[i++] = b;
            } else if (format == 2) {
                int[] temp = new int[input.length];
                int i = 0;
                for (Integer b : (Integer[]) input) temp[i++] = b;
                msg = int32Buffer2Bytes(temp);
            } else {
                msg = string2bytes(((String[]) input)[0]);
            }
            CTX_ECHO ctx = new CTX_ECHO();
            echoInit(ctx);
            echo(ctx, msg);
            int[] r = echoClose(ctx);
            Object[] out;
            if (output == 2) {
                Integer[] ret = new Integer[r.length];
                int i = 0;
                for (int w : r) ret[i++] = w;
                out = ret;
            } else if (output == 1) {
                byte[] x = int32Buffer2Bytes(r);
                Byte[] ret = new Byte[x.length];
                int i = 0;
                for (byte b : x) ret[i++] = b;
                out = ret;
            } else {
                out = new String[1];
                out[0] = int32ArrayToHexString(r);
            }
            return out;
        }
    }
    public static final class Jh{
        public static int Jh_BlockSize = 64;
        public static int Jh_StateSize = 32;

        public static int JH_HX = 8;
        public static int JH_HY = 4;

        public static int[] IV512 = bytes2Int32Buffer(b64Decode("b9FLlj4Aqhdjai4FehXVQ4oiXo0Ml+8L6TQSWfKzw2GJHaDBU2+AHiqpBWvqK22AWI7M2yB1uqapDzp2uvg79wFp5gVB40ppRrWKji5v5loQR6fQwYQ8JDtucbEtWsGZz1f27J2x+FanBoh8VxaxVuPC/N/mhRf7VFpGeMyM3Us="));
        public static int[] C = bytes2Int32Buffer(b64Decode("ot7Vcmf4Fd8KFYR7VxUjt5DWq4H2h1pNxU+fTkAr0cPgOpjqnPpFXJnSxQOambJmtJYCZopTu/IaFFa1MaLbiFxaowPbDhmaCrI/QBBEwYeAGQUcHZWehK3rM2/czedekhO6EEFrvwIVZXjc0Ce79zmBLApQeKo30r8aP9ORAEENWi1CkH7M9pyfYt3Ol8CSC6dcGKxEK8fWZd/RI/zGYwNsbpcauOCefkUFIajsbES7A/Hu+mGOXbKXlv2XgYOUN4WOSi8wA9stjWcqlWqf+4Fz/opsabj4RnLHihRCf8CPFfTFxF7HvadvRHWAuxGPt3XeUryI5K4eALiC9KOmmDOP9I4VY6OpJFZfqon5t9Ug7fG2/eBafFrpyjY2LEIGQzUpzj2Y/k50+TpTp0uac1kf9dCGgU5vga2dDp9a2K9nBgWnamI07r4oC4snF7luJgd0Rz8QgMZvfqDge0h+xqUKVQ3ApPhKn+fjkZ7xjpeBcnaG1I1gUEFann5isOXz7B+f/HogVEAAGuTjhMn0zvWU10/YlfqdEX4uVaVUwyQoct9bKG7+veJ/9XiyxKUP73yJBS7TSe6Fk35Ef1ko6zdpX3BKMSSz8SiGXmXk1h0EdxvH5yC5UehD/nSKh9Qjo+gpffKUdpIJesvdwdkwm/swGx3gG9xbT0kk2r+CnPIxuuek/79wtAVEMg1IvPjeMvyuOznTu1PBw59FoIsp4P0FyeUPCa73EjRwlDTxkEIBt3Gile1E4zaOO+lKmC9PYx1AiBX2bKBLRMFH/69Sh/FKu34wxgrixbZwRuaMbsxWpNWkAMpPvUuEndquGD7IRc5Xc63RZDBozqboZyVcFPKM2qMW4Q7LWAbpM5qZlJogsmAfe4Rvwn+sztEYhdGgoVtZMtMZ3Y3AHJpQRrSlqmdjPZ+6awTkqxnK9n7uVgvqebEfdCEoqTX3venuUTY7WqxXHXbTUHX+wkY6AXB9o6/BNfdC2KSYIOzteHlna54VY4NBqNs66k07w/qDLIMyHztAp/NHJxw08EBZmnYtt2xOPuf9TyHSOY39uO9ZV9xJDJuN2utJK0nXolsNcPNo0K47fYRVjXrw6aX1ZY745PSiuKBTOxA2ngeoDFrsPnWSlGiRT4joVlVcsFtMvLr4mTu743uUh/PW9Np1XRxrciisrmRtszTcUKU0bHHbKLjy4mH4KlGNEDNk2+P8dd1Z8bysHKI/zkM80btnsEPoAspbCjN1oSmITRk0f1xTFrTDlDuSHk15Dtd1dHk/r+6299So6iE5Gr4JfvRcUScjTFMkoybSPDK6ShejRK3Vpm2mPh21CMnyr5g9WYNWPGuRoXz4TE1ghnLMPuJG9sduCLMzmC9edryxpWbWKyrmxO/otvQGNtTBvhWC7nRjIe+8DU7B/WnJU/TEWn2nJlhYBhYUwX4W+uAGPa+Qfj+dYyjj8snSDNKbADDOql8wDNS3FlEqdJgy4PLYMOsNmvjO43uexUuSefG1buZR/9NohgRXTSObMWeW5vOm5swFdQoX2YF2sc5sMhOEUhc8YqIF+LPLK/RHFUd4glRG/0hqkyMHWN84ZWVeTol8/PKOUIb8RC5wMYbKC9CiCUDwTkd4MDnuoGWDOPfRN+le9706LOQmspchb/gTAdHtRKPn3p/vFd+gi9mSJXb294U8vkLcEnzsp9h+sCer2n2NU96oPqqTziWq2GkCvf1D9lr5CHMa2u9fwKUZShczZk2XaiH9TDGYtDVwFUHbuw8e6ptUze2hY9CackCXUb+ddfbib0eR"));

        public static int[] Sb(int[] x, int c) {
            x[3] = ~x[3];
            x[0] ^= (c) & ~x[2];
            int tmp = (c) ^ (x[0] & x[1]);
            x[0] ^= x[2] & x[3];
            x[3] ^= ~x[1] & x[2];
            x[1] ^= x[0] & x[2];
            x[2] ^= x[0] & ~x[3];
            x[0] ^= x[1] | x[3];
            x[3] ^= x[1] & x[2];
            x[1] ^= tmp & x[0];
            x[2] ^= tmp;
            return x;
        }

        public static int[] Lb(int[] x) {
            x[4] ^= x[1];
            x[5] ^= x[2];
            x[6] ^= x[3] ^ x[0];
            x[7] ^= x[0];
            x[0] ^= x[5];
            x[1] ^= x[6];
            x[2] ^= x[7] ^ x[4];
            x[3] ^= x[4];
            return x;
        }

        public static int Ceven(int n, int r) {
            return C[((r) << 3) + 3 - n];
        }

        public static int Codd(int n, int r) {
            return C[((r) << 3) + 7 - n];
        }
        //CB == 0 Ceven
        //CB == 1 Codd
        public static void S(int[] x0, int[] x1, int[] x2, int[] x3, int cb, int r) {
            int[] x = Sb(new int[]{x0[3], x1[3], x2[3], x3[3]}, cb == 0 ? Ceven(3, r) : Codd(3, r));
            x0[3] = x[0];
            x1[3] = x[1];
            x2[3] = x[2];
            x3[3] = x[3];
            x = Sb(new int[]{x0[2], x1[2], x2[2], x3[2]}, cb == 0 ? Ceven(3, r) : Codd(3, r));
            x0[2] = x[0];
            x1[2] = x[1];
            x2[2] = x[2];
            x3[2] = x[3];
            x = Sb(new int[]{x0[1], x1[1], x2[1], x3[1]}, cb == 0 ? Ceven(3, r) : Codd(3, r));
            x0[1] = x[0];
            x1[1] = x[1];
            x2[1] = x[2];
            x3[1] = x[3];
            x = Sb(new int[]{x0[0], x1[0], x2[0], x3[0]}, cb == 0 ? Ceven(3, r) : Codd(3, r));
            x0[0] = x[0];
            x1[0] = x[1];
            x2[0] = x[2];
            x3[0] = x[3];
        }

        public static void L(int[] x0, int[] x1, int[] x2, int[] x3, int[] x4, int[] x5, int[] x6, int[] x7) {
            int[] x = Lb(new int[]{x0[3], x1[3], x2[3], x3[3], x4[3], x5[3], x6[3], x7[3]});
            x0[3] = x[0];
            x1[3] = x[1];
            x2[3] = x[2];
            x3[3] = x[3];
            x4[3] = x[4];
            x5[3] = x[5];
            x6[3] = x[6];
            x7[3] = x[7];
            x = Lb(new int[]{x0[2], x1[2], x2[2], x3[2], x4[2], x5[2], x6[2], x7[2]});
            x0[2] = x[0];
            x1[2] = x[1];
            x2[2] = x[2];
            x3[2] = x[3];
            x4[2] = x[4];
            x5[2] = x[5];
            x6[2] = x[6];
            x7[2] = x[7];
            x = Lb(new int[]{x0[1], x1[1], x2[1], x3[1], x4[1], x5[1], x6[1], x7[1]});
            x0[1] = x[0];
            x1[1] = x[1];
            x2[1] = x[2];
            x3[1] = x[3];
            x4[1] = x[4];
            x5[1] = x[5];
            x6[1] = x[6];
            x7[1] = x[7];
            x = Lb(new int[]{x0[0], x1[0], x2[0], x3[0], x4[0], x5[0], x6[0], x7[0]});
            x0[0] = x[0];
            x1[0] = x[1];
            x2[0] = x[2];
            x3[0] = x[3];
            x4[0] = x[4];
            x5[0] = x[5];
            x6[0] = x[6];
            x7[0] = x[7];
        }

        public static void Wz(int[] x, int c, int n) {
            int t = (x[3] & (c)) << (n);
            x[3] = ((x[3] >> (n)) & (c)) | t;
            t = (x[2] & (c)) << (n);
            x[2] = ((x[2] >> (n)) & (c)) | t;
            t = (x[1] & (c)) << (n);
            x[1] = ((x[1] >> (n)) & (c)) | t;
            t = (x[0] & (c)) << (n);
            x[0] = ((x[0] >> (n)) & (c)) | t;
        }

        public static void W(int ro, int[] x) {
            switch (ro) {
                case 0:
                    Wz(x, (0x55555555), 1);
                    return;
                case 1:
                    Wz(x, (0x33333333), 2);
                    return;
                case 2:
                    Wz(x, (0x0F0F0F0F), 4);
                    return;
                case 3:
                    Wz(x, (0x00FF00FF), 8);
                    return;
                case 4:
                    Wz(x, (0x0000FFFF), 16);
                    return ;
                case 5: {
                    int t = x[3];
                    x[3] = x[2];
                    x[2] = t;
                    t = x[1];
                    x[1] = x[0];
                    x[0] = t;
                    return;
                }
                case 6: {
                    int t = x[3];
                    x[3] = x[1];
                    x[1] = t;
                    t = x[2];
                    x[2] = x[0];
                    x[0] = t;
                    return;
                }
            }
        }

        public static void SL(int[][] h, int r, int ro) {
            S(h[0], h[2], h[4], h[6], 0, r);
            S(h[1], h[3], h[5], h[7], 1, r);
            L(h[0], h[2], h[4], h[6], h[1], h[3], h[5], h[7]);
            W(ro, h[1]);
            W(ro, h[3]);
            W(ro, h[5]);
            W(ro, h[7]);
        }

        public static void READ_STATE(int[][] h, int[] state) {
            h[0][3] = state[0];
            h[0][2] = state[1];
            h[0][1] = state[2];
            h[0][0] = state[3];
            h[1][3] = state[4];
            h[1][2] = state[5];
            h[1][1] = state[6];
            h[1][0] = state[7];
            h[2][3] = state[8];
            h[2][2] = state[9];
            h[2][1] = state[10];
            h[2][0] = state[11];
            h[3][3] = state[12];
            h[3][2] = state[13];
            h[3][1] = state[14];
            h[3][0] = state[15];
            h[4][3] = state[16];
            h[4][2] = state[17];
            h[4][1] = state[18];
            h[4][0] = state[19];
            h[5][3] = state[20];
            h[5][2] = state[21];
            h[5][1] = state[22];
            h[5][0] = state[23];
            h[6][3] = state[24];
            h[6][2] = state[25];
            h[6][1] = state[26];
            h[6][0] = state[27];
            h[7][3] = state[28];
            h[7][2] = state[29];
            h[7][1] = state[30];
            h[7][0] = state[31];
        }

        public static void WRITE_STATE(int[][] h, int[] state) {
            state[0] = h[0][3];
            state[1] = h[0][2];
            state[2] = h[0][1];
            state[3] = h[0][0];
            state[4] = h[1][3];
            state[5] = h[1][2];
            state[6] = h[1][1];
            state[7] = h[1][0];
            state[8] = h[2][3];
            state[9] = h[2][2];
            state[10] = h[2][1];
            state[11] = h[2][0];
            state[12] = h[3][3];
            state[13] = h[3][2];
            state[14] = h[3][1];
            state[15] = h[3][0];
            state[16] = h[4][3];
            state[17] = h[4][2];
            state[18] = h[4][1];
            state[19] = h[4][0];
            state[20] = h[5][3];
            state[21] = h[5][2];
            state[22] = h[5][1];
            state[23] = h[5][0];
            state[24] = h[6][3];
            state[25] = h[6][2];
            state[26] = h[6][1];
            state[27] = h[6][0];
            state[28] = h[7][3];
            state[29] = h[7][2];
            state[30] = h[7][1];
            state[31] = h[7][0];
        }

        public static void E8(int[][] h) {
            for (int r = 0; r < 42; r += 7) {
                SL(h, r + 0, 0);
                SL(h, r + 1, 1);
                SL(h, r + 2, 2);
                SL(h, r + 3, 3);
                SL(h, r + 4, 4);
                SL(h, r + 5, 5);
                SL(h, r + 6, 6);
            }
        }

        public static void bufferXORInsertBackwards(int[][] buffer, int[] data, int x, int y, int bufferOffsetX, int bufferOffsetY) {
            if (bufferOffsetX == 0) bufferOffsetX = 0;
            if (bufferOffsetY == 0) bufferOffsetY = 0;
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < x; j++) {
                    int m = i + bufferOffsetX;
                    int n = bufferOffsetY + y - 1 - j;
                    int xOr = buffer[m][n] ^ data[i * 4 + j];
                    buffer[m][n] = xOr;
                }
            }
        }

        public static void jh(CTX_JH ctx, byte[] data, int len) {
            byte[] buf;
            int ptr;
            //create a local copy of states
            buf = ctx.buffer;
            ptr = ctx.ptr;
            if (len == 0) len = data.length;
            if (len < ctx.buffer.length - ptr) {
                bufferInsert(buf, ptr, data, data.length, 0);
                ptr += data.length;
                ctx.ptr = ptr;
                return;
            }
            int[][] V = new int[JH_HX][];
            for (int i = 0; i < JH_HX; i++) {
                V[i] = new int[JH_HY];
            }
            READ_STATE(V, ctx.state);
            while (len > 0) {
                int clen = ctx.buffer.length - ptr;
                if (clen > len) clen = len;
                bufferInsert(buf, ptr, data, clen, 0);
                ptr += clen;
                data = slice(data, clen);
                len -= clen;
                if (ptr == ctx.buffer.length) {
                    int[] int32Buf = swap32Array(bytes2Int32Buffer(buf));

                    bufferXORInsertBackwards(V, int32Buf, 4, 4, 0, 0);
                    E8(V);
                    bufferXORInsertBackwards(V, int32Buf, 4, 4, 4, 0);
                    if ((ctx.blockCountLow = t32(ctx.blockCountLow + 1)) == 0)
                        ctx.blockCountHigh++;
                    ptr = 0;
                }
            }
            WRITE_STATE(V, ctx.state);
            ctx.ptr = ptr;
        }

        public static int[] jhClose(CTX_JH ctx) {
            int z;
            byte[] buf = new byte[128];
            int numz, u;
            int[] l = new int[4];
            buf[0] = (byte)0x80;
            if (ctx.ptr == 0) {
                numz = 47;
            } else {
                numz = 111 - ctx.ptr;
            }
            bufferSet(buf, 1, (byte)0, numz);
            l[0] = t32(ctx.blockCountLow << 9) + (ctx.ptr << 3);
            l[1] = t32(ctx.blockCountLow >> 23) + t32(ctx.blockCountHigh << 9);
            l[2] = t32(ctx.blockCountHigh >> 23);
            l[3] = 0;
            byte[] lBytes = int32Buffer2Bytes(swap32Array(l));
            bufferInsertBackwards(buf, 1 + numz, lBytes, 16);
            jh(ctx, buf, numz + 17);
            int[] out = new int[16];
            for (u = 0; u < 16; u++)
                out[u] = swap32(ctx.state[u + 16]);
            return out;
        }

        public static Object[] jhExports(Object[] input, int format, int output) {
            byte[] msg;
            if (format == 1) {
                msg = new byte[input.length];
                int i = 0;
                for (Byte b : (Byte[]) input) msg[i++] = b;
            } else if (format == 2) {
                int[] temp = new int[input.length];
                int i = 0;
                for (Integer b : (Integer[]) input) temp[i++] = b;
                msg = int32Buffer2Bytes(temp);
            } else {
                msg = string2bytes(((String[]) input)[0]);
            }
            CTX_JH ctx = new CTX_JH();
            ctx.state = swap32Array(IV512);
            ctx.ptr = 0;
            ctx.buffer = new byte[Jh_BlockSize];
            ctx.blockCountHigh = 0;
            ctx.blockCountLow = 0;
            jh(ctx, msg, 0);
            int[] r = jhClose(ctx);
            Object[] out;
            if (output == 2) {
                Integer[] ret = new Integer[r.length];
                int i = 0;
                for (int w : r) ret[i++] = w;
                out = ret;
            } else if (output == 1) {
                byte[] x = int32Buffer2Bytes(r);
                Byte[] ret = new Byte[x.length];
                int i = 0;
                for (byte b : x) ret[i++] = b;
                out = ret;
            } else {
                out = new String[1];
                out[0] = int32ArrayToHexString(r);
            }
            return out;
        }
    }
    public static final class Keccak{
        public static char[] HEX_CHARS = "0123456789abcdef".toCharArray();
        public static int[] KECCAK_PADDING = {1, 256, 65536, 16777216};
        public static int[] SHIFT = {0, 8, 16, 24};
        public static long[] RC = new long[]{1, 0, 32898, 0, 32906, 2147483648L, 2147516416L, 2147483648L, 32907, 0, 2147483649L,
                0, 2147516545L, 2147483648L, 32777, 2147483648L, 138, 0, 136, 0, 2147516425L, 0,
                2147483658L, 0, 2147516555L, 0, 139, 2147483648L, 32905, 2147483648L, 32771,
                2147483648L, 32770, 2147483648L, 128, 2147483648L, 32778, 0, 2147483658L, 2147483648L,
                2147516545L, 2147483648L, 32896, 2147483648L, 2147483649L, 0, 2147516424L, 2147483648L
        };
        public static int BITS = 512;
        public static String[] OUTPUT_TYPES = {"hex", "buffer", "array"};


        public int outputBits, block, start, blockCount, byteCount, outputBlocks, extraBytes, lastByteIndex;
        public int[] blocks, s, padding;
        public boolean reset;

        public Keccak(int bits, int[] padding, int outputBits) {
            this.s = new int[50];
            this.padding = padding;
            this.outputBits = outputBits;
            this.reset = true;
            this.block = 0;
            this.start = 0;
            this.blockCount = (1600 - (bits << 1)) >> 5;
            this.blocks = new int[blockCount];
            this.byteCount = this.blockCount << 2;
            this.outputBlocks = outputBits >> 5;
            this.extraBytes = (outputBits & 31) >> 3;

            for (int i = 0; i < 50; ++i) {
                this.s[i] = 0;
            }
        }

        public Keccak update(Object[] input) {
            byte[] message;
            boolean notString = !(input[0] instanceof String);
            if (notString && (input instanceof Byte[])) {
                message = new byte[input.length];
                int i = 0;
                for(Object o : input) message[i++] = (Byte) o;
            } else {
                message = string2bytes((String)input[0]);
            }
            int length = message.length;
            int[] blocks = this.blocks;
            int byteCount = this.byteCount;
            int blockCount = this.blockCount;
            int index = 0;
            int[]  s = this.s;
            int i, code;

            while (index < length) {
                if (this.reset) {
                    this.reset = false;
                    blocks[0] = this.block;
                    for (i = 1; i < blockCount + 1; ++i) {
                        blocks[i] = 0;
                    }
                }
                if (notString) {
                    for (i = this.start; index < length && i < byteCount; ++index) {
                        blocks[i >> 2] |= message[index] << SHIFT[i++ & 3];
                    }
                } else {
                    for (i = this.start; index < length && i < byteCount; ++index) {
                        code = message[index];
                        if (code < 0x80) {
                            blocks[i >> 2] |= code << SHIFT[i++ & 3];
                        } else if (code < 0x800) {
                            blocks[i >> 2] |= (0xc0 | (code >> 6)) << SHIFT[i++ & 3];
                            blocks[i >> 2] |= (0x80 | (code & 0x3f)) << SHIFT[i++ & 3];
                        } else if (code < 0xd800 || code >= 0xe000) {
                            blocks[i >> 2] |= (0xe0 | (code >> 12)) << SHIFT[i++ & 3];
                            blocks[i >> 2] |= (0x80 | ((code >> 6) & 0x3f)) << SHIFT[i++ & 3];
                            blocks[i >> 2] |= (0x80 | (code & 0x3f)) << SHIFT[i++ & 3];
                        } else {
                            code = 0x10000 + (((code & 0x3ff) << 10) | (message[++index] & 0x3ff));
                            blocks[i >> 2] |= (0xf0 | (code >> 18)) << SHIFT[i++ & 3];
                            blocks[i >> 2] |= (0x80 | ((code >> 12) & 0x3f)) << SHIFT[i++ & 3];
                            blocks[i >> 2] |= (0x80 | ((code >> 6) & 0x3f)) << SHIFT[i++ & 3];
                            blocks[i >> 2] |= (0x80 | (code & 0x3f)) << SHIFT[i++ & 3];
                        }
                    }
                }
                this.lastByteIndex = i;
                if (i >= byteCount) {
                    this.start = i - byteCount;
                    this.block = blocks[blockCount];
                    for (i = 0; i < blockCount; ++i) {
                        s[i] ^= blocks[i];
                    }
                    f(s);
                    this.reset = true;
                } else {
                    this.start = i;
                }
            }
            return this;
        }

        public void finalize() {
            int[] blocks = this.blocks;
            int i = this.lastByteIndex;
            int blockCount = this.blockCount;
            int[] s = this.s;
            blocks[i >> 2] |= this.padding[i & 3];
            if (this.lastByteIndex == this.byteCount) {
                blocks[0] = blocks[blockCount];
                for (i = 1; i < blockCount + 1; ++i) {
                    blocks[i] = 0;
                }
            }
            blocks[blockCount - 1] |= 0x80000000;
            for (i = 0; i < blockCount; ++i) {
                s[i] ^= blocks[i];
            }
            f(s);
        }

        public String toString(){
            return this.hex();
        }
        public String hex() {
            this.finalize();

            int blockCount = this.blockCount;
            int[] s = this.s;
            int outputBlocks = this.outputBlocks;
            int extraBytes = this.extraBytes;
            int i = 0, j = 0;
            String hex = "";
            int block;
            while (j < outputBlocks) {
                for (i = 0; i < blockCount && j < outputBlocks; ++i, ++j) {
                    block = s[i];
                    hex += HEX_CHARS[(block >> 4) & 0x0F] + HEX_CHARS[block & 0x0F] +
                            HEX_CHARS[(block >> 12) & 0x0F] + HEX_CHARS[(block >> 8) & 0x0F] +
                            HEX_CHARS[(block >> 20) & 0x0F] + HEX_CHARS[(block >> 16) & 0x0F] +
                            HEX_CHARS[(block >> 28) & 0x0F] + HEX_CHARS[(block >> 24) & 0x0F];
                }
                if (j % blockCount == 0) {
                    f(s);
                    i = 0;
                }
            }
            if (extraBytes != 0) {
                block = s[i];
                if (extraBytes > 0) {
                    hex += HEX_CHARS[(block >> 4) & 0x0F] + HEX_CHARS[block & 0x0F];
                }
                if (extraBytes > 1) {
                    hex += HEX_CHARS[(block >> 12) & 0x0F] + HEX_CHARS[(block >> 8) & 0x0F];
                }
                if (extraBytes > 2) {
                    hex += HEX_CHARS[(block >> 20) & 0x0F] + HEX_CHARS[(block >> 16) & 0x0F];
                }
            }
            return hex;
        }

        public byte[] buffer() {
            this.finalize();

            int blockCount = this.blockCount;
            int[] s = this.s;
            int outputBlocks = this.outputBlocks;
            int extraBytes = this.extraBytes;
            int i = 0, j = 0;

            int bytes = this.outputBits >> 3;
            byte[] buffer;
            if (extraBytes != 0) {
                buffer = new byte[((outputBlocks + 1) << 2)];
            } else {
                buffer = new byte[bytes];
            }
            int[] array = new int[buffer.length];
            while (j < outputBlocks) {
                for (i = 0; i < blockCount && j < outputBlocks; ++i, ++j) {
                    array[j] = s[i];
                }
                if (j % blockCount == 0) {
                    f(s);
                }
            }
            if (extraBytes != 0) {
                array[i] = s[i];
                buffer = slice(buffer, bytes);
            }
            return buffer;
        }

        public byte[] digest() {
            this.finalize();

            int blockCount = this.blockCount;
            int[] s = this.s;
            int outputBlocks = this.outputBlocks;
            int extraBytes = this.extraBytes;
            int i = 0, j = 0;

            byte[] array = new byte[outputBlocks << 2 + 3];
            int offset, block;
            while (j < outputBlocks) {
                for (i = 0; i < blockCount && j < outputBlocks; ++i, ++j) {
                    offset = j << 2;
                    block = s[i];
                    array[offset] = (byte)(block & 0xFF);
                    array[offset + 1] = (byte)((block >> 8) & 0xFF);
                    array[offset + 2] = (byte)((block >> 16) & 0xFF);
                    array[offset + 3] = (byte)((block >> 24) & 0xFF);
                }
                if (j % blockCount == 0) {
                    f(s);
                }
            }
            if (extraBytes != 0) {
                offset = j << 2;
                block = s[i];
                if (extraBytes > 0) {
                    array[offset] = (byte)(block & 0xFF);
                }
                if (extraBytes > 1) {
                    array[offset + 1] = (byte)((block >> 8) & 0xFF);
                }
                if (extraBytes > 2) {
                    array[offset + 2] = (byte)((block >> 16) & 0xFF);
                }
            }
            return array;
        }

        public byte[] array(){
            return digest();
        }

        public void f(int[] s) {
            int h, l, n, c0, c1, c2, c3, c4, c5, c6, c7, c8, c9,
                    b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17,
                    b18, b19, b20, b21, b22, b23, b24, b25, b26, b27, b28, b29, b30, b31, b32, b33,
                    b34, b35, b36, b37, b38, b39, b40, b41, b42, b43, b44, b45, b46, b47, b48, b49;
            for (n = 0; n < 48; n += 2) {
                c0 = s[0] ^ s[10] ^ s[20] ^ s[30] ^ s[40];
                c1 = s[1] ^ s[11] ^ s[21] ^ s[31] ^ s[41];
                c2 = s[2] ^ s[12] ^ s[22] ^ s[32] ^ s[42];
                c3 = s[3] ^ s[13] ^ s[23] ^ s[33] ^ s[43];
                c4 = s[4] ^ s[14] ^ s[24] ^ s[34] ^ s[44];
                c5 = s[5] ^ s[15] ^ s[25] ^ s[35] ^ s[45];
                c6 = s[6] ^ s[16] ^ s[26] ^ s[36] ^ s[46];
                c7 = s[7] ^ s[17] ^ s[27] ^ s[37] ^ s[47];
                c8 = s[8] ^ s[18] ^ s[28] ^ s[38] ^ s[48];
                c9 = s[9] ^ s[19] ^ s[29] ^ s[39] ^ s[49];

                h = c8 ^ ((c2 << 1) | (c3 >>> 31));
                l = c9 ^ ((c3 << 1) | (c2 >>> 31));
                s[0] ^= h;
                s[1] ^= l;
                s[10] ^= h;
                s[11] ^= l;
                s[20] ^= h;
                s[21] ^= l;
                s[30] ^= h;
                s[31] ^= l;
                s[40] ^= h;
                s[41] ^= l;
                h = c0 ^ ((c4 << 1) | (c5 >>> 31));
                l = c1 ^ ((c5 << 1) | (c4 >>> 31));
                s[2] ^= h;
                s[3] ^= l;
                s[12] ^= h;
                s[13] ^= l;
                s[22] ^= h;
                s[23] ^= l;
                s[32] ^= h;
                s[33] ^= l;
                s[42] ^= h;
                s[43] ^= l;
                h = c2 ^ ((c6 << 1) | (c7 >>> 31));
                l = c3 ^ ((c7 << 1) | (c6 >>> 31));
                s[4] ^= h;
                s[5] ^= l;
                s[14] ^= h;
                s[15] ^= l;
                s[24] ^= h;
                s[25] ^= l;
                s[34] ^= h;
                s[35] ^= l;
                s[44] ^= h;
                s[45] ^= l;
                h = c4 ^ ((c8 << 1) | (c9 >>> 31));
                l = c5 ^ ((c9 << 1) | (c8 >>> 31));
                s[6] ^= h;
                s[7] ^= l;
                s[16] ^= h;
                s[17] ^= l;
                s[26] ^= h;
                s[27] ^= l;
                s[36] ^= h;
                s[37] ^= l;
                s[46] ^= h;
                s[47] ^= l;
                h = c6 ^ ((c0 << 1) | (c1 >>> 31));
                l = c7 ^ ((c1 << 1) | (c0 >>> 31));
                s[8] ^= h;
                s[9] ^= l;
                s[18] ^= h;
                s[19] ^= l;
                s[28] ^= h;
                s[29] ^= l;
                s[38] ^= h;
                s[39] ^= l;
                s[48] ^= h;
                s[49] ^= l;

                b0 = s[0];
                b1 = s[1];
                b32 = (s[11] << 4) | (s[10] >>> 28);
                b33 = (s[10] << 4) | (s[11] >>> 28);
                b14 = (s[20] << 3) | (s[21] >>> 29);
                b15 = (s[21] << 3) | (s[20] >>> 29);
                b46 = (s[31] << 9) | (s[30] >>> 23);
                b47 = (s[30] << 9) | (s[31] >>> 23);
                b28 = (s[40] << 18) | (s[41] >>> 14);
                b29 = (s[41] << 18) | (s[40] >>> 14);
                b20 = (s[2] << 1) | (s[3] >>> 31);
                b21 = (s[3] << 1) | (s[2] >>> 31);
                b2 = (s[13] << 12) | (s[12] >>> 20);
                b3 = (s[12] << 12) | (s[13] >>> 20);
                b34 = (s[22] << 10) | (s[23] >>> 22);
                b35 = (s[23] << 10) | (s[22] >>> 22);
                b16 = (s[33] << 13) | (s[32] >>> 19);
                b17 = (s[32] << 13) | (s[33] >>> 19);
                b48 = (s[42] << 2) | (s[43] >>> 30);
                b49 = (s[43] << 2) | (s[42] >>> 30);
                b40 = (s[5] << 30) | (s[4] >>> 2);
                b41 = (s[4] << 30) | (s[5] >>> 2);
                b22 = (s[14] << 6) | (s[15] >>> 26);
                b23 = (s[15] << 6) | (s[14] >>> 26);
                b4 = (s[25] << 11) | (s[24] >>> 21);
                b5 = (s[24] << 11) | (s[25] >>> 21);
                b36 = (s[34] << 15) | (s[35] >>> 17);
                b37 = (s[35] << 15) | (s[34] >>> 17);
                b18 = (s[45] << 29) | (s[44] >>> 3);
                b19 = (s[44] << 29) | (s[45] >>> 3);
                b10 = (s[6] << 28) | (s[7] >>> 4);
                b11 = (s[7] << 28) | (s[6] >>> 4);
                b42 = (s[17] << 23) | (s[16] >>> 9);
                b43 = (s[16] << 23) | (s[17] >>> 9);
                b24 = (s[26] << 25) | (s[27] >>> 7);
                b25 = (s[27] << 25) | (s[26] >>> 7);
                b6 = (s[36] << 21) | (s[37] >>> 11);
                b7 = (s[37] << 21) | (s[36] >>> 11);
                b38 = (s[47] << 24) | (s[46] >>> 8);
                b39 = (s[46] << 24) | (s[47] >>> 8);
                b30 = (s[8] << 27) | (s[9] >>> 5);
                b31 = (s[9] << 27) | (s[8] >>> 5);
                b12 = (s[18] << 20) | (s[19] >>> 12);
                b13 = (s[19] << 20) | (s[18] >>> 12);
                b44 = (s[29] << 7) | (s[28] >>> 25);
                b45 = (s[28] << 7) | (s[29] >>> 25);
                b26 = (s[38] << 8) | (s[39] >>> 24);
                b27 = (s[39] << 8) | (s[38] >>> 24);
                b8 = (s[48] << 14) | (s[49] >>> 18);
                b9 = (s[49] << 14) | (s[48] >>> 18);

                s[0] = b0 ^ (~b2 & b4);
                s[1] = b1 ^ (~b3 & b5);
                s[10] = b10 ^ (~b12 & b14);
                s[11] = b11 ^ (~b13 & b15);
                s[20] = b20 ^ (~b22 & b24);
                s[21] = b21 ^ (~b23 & b25);
                s[30] = b30 ^ (~b32 & b34);
                s[31] = b31 ^ (~b33 & b35);
                s[40] = b40 ^ (~b42 & b44);
                s[41] = b41 ^ (~b43 & b45);
                s[2] = b2 ^ (~b4 & b6);
                s[3] = b3 ^ (~b5 & b7);
                s[12] = b12 ^ (~b14 & b16);
                s[13] = b13 ^ (~b15 & b17);
                s[22] = b22 ^ (~b24 & b26);
                s[23] = b23 ^ (~b25 & b27);
                s[32] = b32 ^ (~b34 & b36);
                s[33] = b33 ^ (~b35 & b37);
                s[42] = b42 ^ (~b44 & b46);
                s[43] = b43 ^ (~b45 & b47);
                s[4] = b4 ^ (~b6 & b8);
                s[5] = b5 ^ (~b7 & b9);
                s[14] = b14 ^ (~b16 & b18);
                s[15] = b15 ^ (~b17 & b19);
                s[24] = b24 ^ (~b26 & b28);
                s[25] = b25 ^ (~b27 & b29);
                s[34] = b34 ^ (~b36 & b38);
                s[35] = b35 ^ (~b37 & b39);
                s[44] = b44 ^ (~b46 & b48);
                s[45] = b45 ^ (~b47 & b49);
                s[6] = b6 ^ (~b8 & b0);
                s[7] = b7 ^ (~b9 & b1);
                s[16] = b16 ^ (~b18 & b10);
                s[17] = b17 ^ (~b19 & b11);
                s[26] = b26 ^ (~b28 & b20);
                s[27] = b27 ^ (~b29 & b21);
                s[36] = b36 ^ (~b38 & b30);
                s[37] = b37 ^ (~b39 & b31);
                s[46] = b46 ^ (~b48 & b40);
                s[47] = b47 ^ (~b49 & b41);
                s[8] = b8 ^ (~b0 & b2);
                s[9] = b9 ^ (~b1 & b3);
                s[18] = b18 ^ (~b10 & b12);
                s[19] = b19 ^ (~b11 & b13);
                s[28] = b28 ^ (~b20 & b22);
                s[29] = b29 ^ (~b21 & b23);
                s[38] = b38 ^ (~b30 & b32);
                s[39] = b39 ^ (~b31 & b33);
                s[48] = b48 ^ (~b40 & b42);
                s[49] = b49 ^ (~b41 & b43);

                s[0] ^= RC[n];
                s[1] ^= RC[n + 1];
            }
        }

        public static Object[] keccakExports(Object[] input, int format, int output) {
            byte[] msg;
            if (format == 1) {
                msg = new byte[input.length];
                int i = 0;
                for (Byte b : (Byte[]) input) msg[i++] = b;
            } else if (format == 2) {
                int[] temp = new int[input.length];
                int i = 0;
                for (Integer b : (Integer[]) input) temp[i++] = b;
                msg = int32Buffer2Bytes(temp);
            } else {
                msg = string2bytes(((String[]) input)[0]);
            }
            Byte[] dat = new Byte[msg.length];
            int i = 0;
            for(byte b : msg) dat[i++] = b;
            if (output == 1) {
                int[] arr = bytes2Int32Buffer(new Keccak(0, new int[]{}, 0).update(dat).array());
                Integer[] ret = new Integer[arr.length];
                int w = 0;
                for(int j : arr) ret[i++] = j;
                return ret;
            } else if (output == 2) {
                byte[] arr = new Keccak(0, new int[]{}, 0).update(dat).array();
                Byte[] ret = new Byte[arr.length];
                int w = 0;
                for(byte j : arr) ret[i++] = j;
                return ret;
            } else {
                return new String[]{new Keccak(0, new int[]{}, 0).update(dat).hex()};
            }
        }
    }

    public static String int8ArrayToHexString(byte[] array) {
        String string = "";

        for (int i = 0; i < array.length; i++) {
            if (array[i] < 16) {
                string += '0' + Integer.toHexString(array[i]);
            } else {
                string += Integer.toHexString(array[i]);
            }
        }
        return string;
    }

    public static String int32ArrayToHexString(int[] array) {
        String string = "";
        int len = array.length;
        for (int i = 0; i < len; i++) {
            int s = array[i];
            if (s < 0) {
                s = 0xFFFFFFFF + array[i] + 1;
            }
            String l = Integer.toHexString(s);
            int padding = 8;
            while (l.length() < padding) {
                l = "0" + l;
            }
            string += l;
        }
        return string;
    }

    public static long[] bytes2Int64Buffer(byte[] b) {
        if (b == null) return new long[0];
        int len = b.length > 0 ? (((b.length - 1) >>> 3) + 1) : 0;
        long[] buffer = new long[len];
        int j = 0;
        while (j < len) {
            buffer[j] = (((long) b[j * 8]) << 56) | (((long) b[j * 8 + 1]) << 48) | (((long) b[j * 8 + 2]) << 40) | (((long) b[j * 8 + 2]) << 32) | (((long) b[j * 8 + 2]) << 24) | (((long) b[j * 8 + 2]) << 16) | (((long) b[j * 8 + 2]) << 8) | b[j * 8 + 7];
            j++;
        }
        return buffer;
    }

    public static long[] bytes2Int64BufferLeAligned(byte[] b) {
        if (b == null) return new long[0];
        int len = b.length > 0 ? (((b.length - 1) >>> 3) + 1) : 0;
        long[] buffer = new long[len];
        int j = 0;
        while (j < len) {
            buffer[j] = (((long) b[j * 8 + 7]) << 56) | (((long) b[j * 8 + 6]) << 48) | (((long) b[j * 8 + 5]) << 40) | (((long) b[j * 8 + 4]) << 32) | (((long) b[j * 8 + 3]) << 24) | (((long) b[j * 8 + 2]) << 16) | (((long) b[j * 8 + 1]) << 8) | b[j * 8];
            j++;
        }
        return buffer;
    }

    public static byte[] bufferEncode64(byte[] buffer, int offset, long uint64) {
        buffer[offset + 0] = (byte) (uint64 >>> 56);
        buffer[offset + 1] = (byte) (uint64 >>> 48 & 0xFF);
        buffer[offset + 2] = (byte) (uint64 >>> 40 & 0xFF);
        buffer[offset + 3] = (byte) (uint64 >>> 32 & 0xFF);
        buffer[offset + 4] = (byte) (uint64 >>> 24 & 0xFF);
        buffer[offset + 5] = (byte) (uint64 >>> 16 & 0xFF);
        buffer[offset + 6] = (byte) (uint64 >>> 8 & 0xFF);
        buffer[offset + 7] = (byte) (uint64 & 0xFF);

        return buffer;
    }

    public static byte[] bufferEncode64leAligned(byte[] buffer, int offset, long uint64) {
        buffer[offset + 7] = (byte) (uint64 >>> 56);
        buffer[offset + 6] = (byte) (uint64 >>> 48 & 0xFF);
        buffer[offset + 5] = (byte) (uint64 >>> 40 & 0xFF);
        buffer[offset + 4] = (byte) (uint64 >>> 32 & 0xFF);
        buffer[offset + 3] = (byte) (uint64 >>> 24 & 0xFF);
        buffer[offset + 2] = (byte) (uint64 >>> 16 & 0xFF);
        buffer[offset + 1] = (byte) (uint64 >>> 8 & 0xFF);
        buffer[offset + 0] = (byte) (uint64 & 0xFF);

        return buffer;
    }

    public static int[] bytes2Int32Buffer(byte[] b) {
        if (b == null) return new int[0];
        int len = b.length > 0 ? (((b.length - 1) >>> 2) + 1) : 0;
        int[] buffer = new int[len];
        int j = 0;
        while (j < len) {
            buffer[j] = (b[j * 4] << 24) | (b[j * 4 + 1] << 16) | (b[j * 4 + 2] << 8) | b[j * 4 + 3];
            j++;
        }
        return buffer;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytes2hex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] int32Buffer2Bytes(int[] b) {
        byte[] buffer = new byte[b.length * 4];
        int len = b.length;
        int i = 0;
        while (i < len) {
            buffer[i * 4] = ((byte) ((b[i] & 0xFF000000) >>> 24));
            buffer[i * 4 + 1] = ((byte) ((b[i] & 0x00FF0000) >>> 16));
            buffer[i * 4 + 2] = ((byte) ((b[i] & 0x0000FF00) >>> 8));
            buffer[i * 4 + 3] = ((byte) ((b[i] & 0x000000FF)));
            i++;
        }
        return buffer;
    }

    public static byte[] string2bytes(String s) {
        int len = s.length();
        byte[] b = new byte[len];
        int i = 0;
        while (i < len) {
            b[i] = (byte) s.charAt(i);
            i++;
        }
        return b;
    }

    public static byte[] hex2bytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String hex2string(String x) {
        return new String(hex2bytes(x));
    }

    public static int[] string2Int32Buffer(String s) {
        return bytes2Int32Buffer(string2bytes(s));
    }

    public static String keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    public static String b64Encode(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    public static byte[] b64Decode(String input) {
        return Base64.getDecoder().decode(input);
    }

    public static int[][] AES_ROUND_LE(int[] X, int[] K, int[] Y) {
        (Y[0]) = (byte) (AES0[(X[0]) & 0xFF] ^
                AES1[((X[1]) >>> 8) & 0xFF] ^
                AES2[((X[2]) >>> 16) & 0xFF] ^
                AES3[((X[3]) >>> 24) & 0xFF] ^ (K[0]));
        (Y[1]) = (byte) (AES0[(X[1]) & 0xFF] ^
                AES1[((X[2]) >>> 8) & 0xFF] ^
                AES2[((X[3]) >>> 16) & 0xFF] ^
                AES3[((X[0]) >>> 24) & 0xFF] ^ (K[1]));
        (Y[2]) = (byte) (AES0[(X[2]) & 0xFF] ^
                AES1[((X[3]) >>> 8) & 0xFF] ^
                AES2[((X[0]) >>> 16) & 0xFF] ^
                AES3[((X[1]) >>> 24) & 0xFF] ^ (K[2]));
        (Y[3]) = (byte) (AES0[(X[3]) & 0xFF] ^
                AES1[((X[0]) >>> 8) & 0xFF] ^
                AES2[((X[1]) >>> 16) & 0xFF] ^
                AES3[((X[2]) >>> 24) & 0xFF] ^ (K[3]));
        int[][] ret = new int[3][];
        ret[0] = X;
        ret[1] = K;
        ret[2] = Y;

        return ret;
    }

    public static int[][] AES_ROUND_NOKEY_LE(int[] X, int[] Y) {
        int[] K = new int[]{0, 0, 4};
        return AES_ROUND_LE(X, K, Y);
    }

    public static int t32(long x) {
        return (byte) (x & 0xFFFFFFFF);
    }

    public static int[] swap32Array(int[] a) {
        //can't do this with map because of support for IE8 (Don't hate me plz).
        int i = 0, len = a.length;
        int[] r = new int[a.length];
        while (i < len) {
            r[i] = (swap32(a[i]));
            i++;
        }
        return r;
    }

    public static int swap32(int val) {
        return ((val & 0xFF) << 24) |
                ((val & 0xFF00) << 8) |
                ((val >>> 8) & 0xFF00) |
                ((val >>> 24) & 0xFF);
    }

    public static void buffer2Insert(int[][] buffer, int bufferOffset, int bufferOffset2, int[][] data, int len, int len2) {
        while (len-- > 0) {
            int j = len2;
            while (j-- > 0) {
                buffer[len + bufferOffset][j + bufferOffset2] = data[len][j];
            }
        }
    }

    public static void bufferInsert(int[] buffer, int bufferOffset, int[] data, int len, int dataOffset) {
        dataOffset = dataOffset | 0;
        int i = 0;
        while (i < len) {
            buffer[i + bufferOffset] = data[i + dataOffset];
            i++;
        }
    }

    public static void bufferInsert(byte[] buffer, int bufferOffset, byte[] data, int len, int dataOffset) {
        dataOffset = dataOffset | 0;
        int i = 0;
        System.out.println(data.length);
        while (i < len) {
            byte b = data[i + dataOffset];
            buffer[i + bufferOffset] = b;
            i++;
        }
    }

    public static void bufferSet(int[] buffer, int bufferOffset, int value, int len) {
        int i = 0;
        while (i < len) {
            buffer[i + bufferOffset] = value;
            i++;
        }
    }

    public static void bufferSet(byte[] buffer, int bufferOffset, byte value, int len) {
        int i = 0;
        while (i < len) {
            buffer[i + bufferOffset] = value;
            i++;
        }
    }

    public static void bufferInsertBackwards(byte[] buffer, int bufferOffset, byte[] data, int len) {
        int i = 0;
        while (i < len) {
            buffer[i + bufferOffset] = data[len - 1 - i];
            i++;
        }
    }

    public static void bufferInsertBackwards(int[] buffer, int bufferOffset, int[] data, int len) {
        int i = 0;
        while (i < len) {
            buffer[i + bufferOffset] = data[len - 1 - i];
            i++;
        }
    }

    public static int[] slice(int[] a, int leng) {
        int[] ret = new int[leng];
        for (int i = 0; i < leng; i++) {
            ret[i] = a[i];
        }
        return ret;
    }

    public static byte[] slice(byte[] a, int leng) {
        byte[] ret = new byte[leng];
        for (int i = 0; i < leng; i++) {
            ret[i] = a[i];
        }
        return ret;
    }

    public static class CTX_ECHO {
        public int[][] state;
        public byte[] buffer;
        public int[] C;
        public int ptr;
    }

    public static class CTX_JH {
        public int[] state;
        public byte[] buffer;
        public int ptr;
        public int blockCountLow, blockCountHigh;
    }

}
