package com.zhang.orders.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.orders.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        log.info("BlockException,----{}",e.getRule());
        Result result = null;
        if(e instanceof FlowException){
            result = Result.error(100,"接口限流了");
        } else if (e instanceof DegradeException) {
            result = Result.error(101,"接口降级了");
        }else if (e instanceof ParamFlowException) {
            result = Result.error(102,"热点参数限流了");
        }else if (e instanceof SystemBlockException) {
            result = Result.error(103,"触发系统保护规则了");
        }else if (e instanceof AuthorityException) {
            result = Result.error(104,"授权规则不通过");
        }
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(),result);
    }
}
