# 3D座位系统API设计文档

## 概述
本文档描述了剧场3D座位系统的后端API接口设计，包括座位信息获取、选座、锁定、支付等功能。

## 基础数据结构

### 剧场信息 (Theater)
```json
{
  "id": "theater_001",
  "name": "国家大剧院",
  "address": "北京市西城区西长安街2号",
  "capacity": 2000,
  "sections": [
    {
      "id": "section_a",
      "name": "A区",
      "rows": 10,
      "columns": 20,
      "price": 180,
      "category": "premium"
    }
  ],
  "layout": {
    "width": 800,
    "height": 600,
    "stage_position": "bottom",
    "aisles": [
      {"row": 5, "type": "horizontal"},
      {"column": 10, "type": "vertical"}
    ]
  }
}
```

### 座位信息 (Seat)
```json
{
  "id": "seat_a1_1",
  "theater_id": "theater_001",
  "section_id": "section_a",
  "row": 1,
  "column": 1,
  "status": "available", // available, occupied, locked, maintenance
  "price": 180,
  "coordinates": {
    "x": 100,
    "y": 200,
    "z": 0
  },
  "view_angle": {
    "horizontal": 45,
    "vertical": 30
  },
  "accessibility": {
    "wheelchair_accessible": false,
    "near_exit": true
  }
}
```

### 演出信息 (Performance)
```json
{
  "id": "performance_001",
  "theater_id": "theater_001",
  "play_id": "play_001",
  "date": "2024-12-25T19:30:00Z",
  "duration": 120,
  "status": "upcoming",
  "seat_availability": {
    "total": 2000,
    "available": 1500,
    "occupied": 400,
    "locked": 100
  }
}
```

## API接口设计

### 1. 获取剧场3D布局信息

**GET** `/api/theaters/{theater_id}/3d-layout`

**响应:**
```json
{
  "success": true,
  "data": {
    "theater": {
      "id": "theater_001",
      "name": "国家大剧院",
      "layout": {
        "width": 800,
        "height": 600,
        "depth": 400,
        "stage": {
          "position": "bottom",
          "width": 200,
          "height": 40
        },
        "sections": [
          {
            "id": "section_a",
            "name": "A区",
            "position": {
              "x": 0,
              "y": 0,
              "z": 0
            },
            "dimensions": {
              "width": 400,
              "height": 200,
              "depth": 50
            },
            "rows": 10,
            "columns": 20,
            "row_spacing": 30,
            "column_spacing": 20
          }
        ]
      }
    }
  }
}
```

### 2. 获取演出座位状态

**GET** `/api/performances/{performance_id}/seats`

**查询参数:**
- `section_id` (可选): 指定区域
- `status` (可选): 座位状态筛选

**响应:**
```json
{
  "success": true,
  "data": {
    "performance_id": "performance_001",
    "seats": [
      {
        "id": "seat_a1_1",
        "section_id": "section_a",
        "row": 1,
        "column": 1,
        "status": "available",
        "price": 180,
        "coordinates": {
          "x": 100,
          "y": 200,
          "z": 0
        },
        "view_angle": {
          "horizontal": 45,
          "vertical": 30
        }
      }
    ],
    "statistics": {
      "total": 2000,
      "available": 1500,
      "occupied": 400,
      "locked": 100
    }
  }
}
```

### 3. 实时座位状态更新 (WebSocket)

**WebSocket连接:** `ws://api.example.com/ws/seats/{performance_id}`

**消息格式:**
```json
{
  "type": "seat_update",
  "data": {
    "seat_id": "seat_a1_1",
    "status": "locked",
    "user_id": "user_123",
    "timestamp": "2024-12-25T19:30:00Z"
  }
}
```

### 4. 锁定座位

**POST** `/api/seats/lock`

**请求体:**
```json
{
  "performance_id": "performance_001",
  "seat_ids": ["seat_a1_1", "seat_a1_2"],
  "user_id": "user_123",
  "lock_duration": 300 // 锁定时间（秒）
}
```

**响应:**
```json
{
  "success": true,
  "data": {
    "lock_id": "lock_001",
    "locked_seats": ["seat_a1_1", "seat_a1_2"],
    "expires_at": "2024-12-25T19:35:00Z",
    "total_price": 360
  }
}
```

### 5. 释放座位锁定

**DELETE** `/api/seats/lock/{lock_id}`

**响应:**
```json
{
  "success": true,
  "message": "座位锁定已释放"
}
```

### 6. 确认选座并创建订单

**POST** `/api/orders/create`

**请求体:**
```json
{
  "performance_id": "performance_001",
  "lock_id": "lock_001",
  "user_id": "user_123",
  "contact_info": {
    "name": "张三",
    "phone": "13800138000",
    "email": "zhangsan@example.com"
  }
}
```

**响应:**
```json
{
  "success": true,
  "data": {
    "order_id": "order_001",
    "performance": {
      "id": "performance_001",
      "play_name": "《哈姆雷特》",
      "date": "2024-12-25T19:30:00Z",
      "theater_name": "国家大剧院"
    },
    "seats": [
      {
        "id": "seat_a1_1",
        "section": "A区",
        "row": 1,
        "column": 1,
        "price": 180
      }
    ],
    "total_amount": 360,
    "service_fee": 20,
    "grand_total": 380,
    "expires_at": "2024-12-25T19:45:00Z"
  }
}
```

### 7. 支付确认

**POST** `/api/orders/{order_id}/pay`

**请求体:**
```json
{
  "payment_method": "wechat_pay",
  "payment_data": {
    "transaction_id": "wx_transaction_123"
  }
}
```

**响应:**
```json
{
  "success": true,
  "data": {
    "order_id": "order_001",
    "payment_status": "paid",
    "tickets": [
      {
        "ticket_id": "ticket_001",
        "qr_code": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
        "seat_info": "A区1排1号"
      }
    ]
  }
}
```

### 8. 获取座位视角预览

**GET** `/api/seats/{seat_id}/view-preview`

**响应:**
```json
{
  "success": true,
  "data": {
    "seat_id": "seat_a1_1",
    "view_image": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQ...",
    "view_angle": {
      "horizontal": 45,
      "vertical": 30
    },
    "distance_to_stage": 15.5,
    "obstruction_info": {
      "has_obstruction": false,
      "description": "视野良好"
    }
  }
}
```

### 9. 获取剧场3D模型

**GET** `/api/theaters/{theater_id}/3d-model`

**响应:**
```json
{
  "success": true,
  "data": {
    "model_url": "https://cdn.example.com/models/theater_001.glb",
    "texture_url": "https://cdn.example.com/textures/theater_001.jpg",
    "model_info": {
      "format": "glb",
      "version": "2.0",
      "size": "2.5MB",
      "polygons": 50000
    }
  }
}
```

## 错误处理

### 标准错误响应格式
```json
{
  "success": false,
  "error": {
    "code": "SEAT_ALREADY_OCCUPIED",
    "message": "座位已被占用",
    "details": {
      "seat_id": "seat_a1_1",
      "occupied_by": "user_456"
    }
  }
}
```

### 常见错误代码
- `SEAT_NOT_FOUND`: 座位不存在
- `SEAT_ALREADY_OCCUPIED`: 座位已被占用
- `SEAT_ALREADY_LOCKED`: 座位已被锁定
- `LOCK_EXPIRED`: 座位锁定已过期
- `INSUFFICIENT_PERMISSIONS`: 权限不足
- `PERFORMANCE_NOT_FOUND`: 演出不存在
- `THEATER_NOT_FOUND`: 剧场不存在

## 性能优化建议

### 1. 缓存策略
- 剧场布局信息缓存24小时
- 座位状态缓存5分钟
- 3D模型文件缓存7天

### 2. 数据库优化
- 座位状态表按演出ID分区
- 建立座位ID、演出ID、状态复合索引
- 使用Redis缓存热点座位状态

### 3. 实时更新优化
- 使用WebSocket推送座位状态变化
- 批量更新座位状态
- 实现座位状态变更的幂等性

## 安全考虑

### 1. 防刷机制
- 限制用户同时锁定的座位数量
- 实现座位锁定的时间窗口
- 检测异常选座行为

### 2. 数据验证
- 验证座位ID的有效性
- 检查用户权限
- 防止重复选座

### 3. 支付安全
- 使用HTTPS传输
- 实现支付签名验证
- 防止重复支付

## 部署建议

### 1. 服务器配置
- 使用负载均衡器分发请求
- 配置WebSocket连接池
- 实现数据库读写分离

### 2. 监控告警
- 监控座位锁定成功率
- 监控支付成功率
- 监控系统响应时间

### 3. 备份策略
- 定期备份座位状态数据
- 备份订单数据
- 实现数据恢复机制
