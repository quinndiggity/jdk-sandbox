/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

#include <Rsocket.h>
#include <dlfcn.h>
#include "jni_util.h"

static const char* nativeRdmaLib = "librdmacm.so.1";
static jboolean funcs_loaded = JNI_FALSE;
 
jboolean loadRdmaFuncs(JNIEnv* env) {
    if (funcs_loaded)
        return JNI_TRUE;

    if (dlopen(nativeRdmaLib, RTLD_GLOBAL | RTLD_LAZY) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_socket = (rs_rsocket_func*)
            dlsym(RTLD_DEFAULT, "rsocket")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_fcntl = (rs_rfcntl_func*)
            dlsym(RTLD_DEFAULT, "rfcntl")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_listen = (rs_rlisten_func*)
            dlsym(RTLD_DEFAULT, "rlisten")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_bind = (rs_rbind_func*)
            dlsym(RTLD_DEFAULT, "rbind")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_connect = (rs_rconnect_func*)
            dlsym(RTLD_DEFAULT, "rconnect")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_getsockname = (rs_rgetsockname_func*)
            dlsym(RTLD_DEFAULT, "rgetsockname")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_getsockopt = (rs_rgetsockopt_func*)
            dlsym(RTLD_DEFAULT, "rgetsockopt")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_setsockopt = (rs_rsetsockopt_func*)
            dlsym(RTLD_DEFAULT, "rsetsockopt")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_shutdown = (rs_rshutdown_func*)
            dlsym(RTLD_DEFAULT, "rshutdown")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_poll = (rs_rpoll_func*)
            dlsym(RTLD_DEFAULT, "rpoll")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_send = (rs_rsend_func*)
            dlsym(RTLD_DEFAULT, "rsend")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_accept = (rs_raccept_func*)
            dlsym(RTLD_DEFAULT, "raccept")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_close = (rs_rclose_func*)
            dlsym(RTLD_DEFAULT, "rclose")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_read = (rs_rread_func*)
            dlsym(RTLD_DEFAULT, "rread")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_readv = (rs_rreadv_func*)
            dlsym(RTLD_DEFAULT, "rreadv")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_write = (rs_rwrite_func*)
            dlsym(RTLD_DEFAULT, "rwrite")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_writev = (rs_rwritev_func*)
            dlsym(RTLD_DEFAULT, "rwritev")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_recv = (rs_rrecv_func*)
            dlsym(RTLD_DEFAULT, "rrecv")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_recvfrom = (rs_rrecvfrom_func*)
            dlsym(RTLD_DEFAULT, "rrecvfrom")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    if ((rs_sendto = (rs_rsendto_func*)
            dlsym(RTLD_DEFAULT, "rsendto")) == NULL) {
        JNU_ThrowByName(env, "java/lang/UnsupportedOperationException",
              dlerror());
        return JNI_FALSE;
    }

    funcs_loaded = JNI_TRUE;
    return JNI_TRUE;
}
