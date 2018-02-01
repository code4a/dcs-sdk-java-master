/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baidu.duer.dcs.devicemodule.cmccgateway;

/**
 * 定义了表示Alerts模块的namespace、name，以及其事件、指令的name
 * <p>
 * Created by wuruisheng on 2017/6/1.
 */
public class ApiConstants {
    public static final String NAMESPACE = "ai.dueros.device_interface.thirdparty.cmcc.smart_gateway";
    public static final String NAME = "SmartGatewayInterface";

    public static final class Events {

    }

    public static final class Directives {
        public static final class Add {
            public static final String NAME = Add.class.getSimpleName();
        }

        public static final class Remove {
            public static final String NAME = Remove.class.getSimpleName();
        }

        public static final class Bind {
            public static final String NAME = Bind.class.getSimpleName();
        }

        public static final class UnBind {
            public static final String NAME = UnBind.class.getSimpleName();
        }
    }
}
