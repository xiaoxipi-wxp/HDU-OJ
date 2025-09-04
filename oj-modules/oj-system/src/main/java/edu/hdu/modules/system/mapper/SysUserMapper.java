package edu.hdu.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.hdu.modules.system.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
